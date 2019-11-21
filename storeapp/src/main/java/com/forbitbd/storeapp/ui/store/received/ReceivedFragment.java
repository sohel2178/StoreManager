package com.forbitbd.storeapp.ui.store.received;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forbitbd.androidutils.dialog.delete.DeleteDialog;
import com.forbitbd.androidutils.dialog.delete.DialogClickListener;
import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.models.Receive;
import com.forbitbd.storeapp.models.Supplier;
import com.forbitbd.storeapp.ui.store.StoreBaseFragment;
import com.forbitbd.storeapp.ui.store.received.receiveDetail.ReceivedDetail;
import com.forbitbd.storeapp.utils.Constant;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReceivedFragment extends StoreBaseFragment implements ReceivedContract.View,ReceiveListener {

    private ReceivedPresenter mPresenter;

    private HashSet<String> set = new HashSet<>();
    private HashSet<String> nameSet = new HashSet<>();


    private ReceiveAdapter adapter;


    public ReceivedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ReceivedPresenter(this);
        adapter = new ReceiveAdapter(getContext(),this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_received, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        mPresenter.getProjectReceives(getProject().get_id());
    }


    @Override
    public List<String> getUnits() {
        return new ArrayList<>(set);
    }

    @Override
    public List<String> getMaterialNames() {
        return new ArrayList<>(nameSet);
    }

    @Override
    public void renderAdapter(List<Receive> receiveList) {
        adapter.setItems(receiveList);

        for(Receive x: receiveList){
            set.add(x.getUnit());
            nameSet.add(x.getName());
        }
    }

    @Override
    public void addItem(Receive receive) {
        if(adapter!=null){
            adapter.addToFirst(receive);
            set.add(receive.getUnit());
            nameSet.add(receive.getName());
        }
    }

    @Override
    public void updateItem(Receive receive) {
        adapter.update(receive);
    }

    @Override
    public void removeFromAdapter(Receive receive) {
        adapter.remove(receive);
    }

    @Override
    public void removeSupplierReceive(Supplier supplier) {
        if(adapter!=null){
            adapter.removeSupplierItems(supplier);
        }
    }

    @Override
    public void onItemClick(int position) {
        Log.d("YYYYYYY","Clicked "+position);
        ReceivedDetail receivedDetail = new ReceivedDetail();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.RECEIVED,adapter.getItem(position));
        receivedDetail.setArguments(bundle);

        receivedDetail.show(getChildFragmentManager(),"HHHHH");

    }

    @Override
    public void onItemUpdate(int position) {
        get_activity().startUpdateReceiveActivity(adapter.getItem(position));
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
                mPresenter.deleteReceive(adapter.getItem(position));
            }
        });

        deleteDialog.show(getChildFragmentManager(),"HHH");
    }

    @Override
    public void onImageClick(int position) {
        Receive receive = adapter.getItem(position);
        get_activity().startZoomImageActivity(receive.getImage());
    }

    public void filter(String query){
        adapter.getFilter().filter(query);
    }
}
