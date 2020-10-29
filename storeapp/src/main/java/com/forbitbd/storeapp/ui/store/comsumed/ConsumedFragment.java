package com.forbitbd.storeapp.ui.store.comsumed;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forbitbd.androidutils.dialog.delete.DeleteDialog;
import com.forbitbd.androidutils.dialog.delete.DialogClickListener;
import com.forbitbd.androidutils.models.Consume;
import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.ui.store.StoreBaseFragment;
import com.forbitbd.storeapp.ui.store.comsumed.consumeDetail.ConsumeDetail;
import com.forbitbd.storeapp.utils.Constant;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConsumedFragment extends StoreBaseFragment implements ConsumedContract.View,ConsumeListener {

    private ConsumeAdapter adapter;
    private ConsumedPresenter mPresenter;

    public ConsumedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ConsumeAdapter(getContext(),this);
        mPresenter = new ConsumedPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_supplier, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();

        if(adapter.getItemCount()<=0){
            mPresenter.getProjectConsumes(getProject().get_id());
        }
    }

    @Override
    public void renderAdapter(List<Consume> consumeList) {
        adapter.setItems(consumeList);
    }

    @Override
    public void addItem(Consume consume) {
        if(adapter!=null){
            adapter.addToFirst(consume);
        }

    }

    @Override
    public void updateItem(Consume consume) {
        adapter.update(consume,adapter.getPosition(consume));
    }

    @Override
    public void removeFromAdapter(Consume consume) {
        adapter.remove(adapter.getPosition(consume));

    }

    @Override
    public void onImageClick(int position) {
        get_activity().startZoomImageActivity(adapter.getItem(position).getImage());

    }

    @Override
    public void onItemClick(int position) {
        ConsumeDetail consumeDetail = new ConsumeDetail();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.CONSUME,adapter.getItem(position));
        consumeDetail.setArguments(bundle);
        consumeDetail.show(getChildFragmentManager(),"HHHHH");
    }

    @Override
    public void onItemUpdate(int position) {
        get_activity().startUpdateConsumeActivity(adapter.getItem(position));
    }

    @Override
    public void onItemRemove(final int position) {
        final DeleteDialog deleteDialog = new DeleteDialog();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.CONTENT,"Really want to delete this Item??");
        deleteDialog.setArguments(bundle);
        deleteDialog.setListener(new DialogClickListener() {
            @Override
            public void positiveButtonClick() {
                deleteDialog.dismiss();
                mPresenter.deleteConsume(adapter.getItem(position));
            }
        });

        deleteDialog.show(getChildFragmentManager(),"HHH");
    }

    public void filter(String query){
        adapter.getFilter().filter(query);
    }
}
