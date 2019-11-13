package com.forbitbd.storeapp.ui.store.comsumed;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.models.Consume;
import com.forbitbd.storeapp.models.Receive;
import com.forbitbd.storeapp.ui.store.StoreBaseFragment;

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
        View view = inflater.inflate(R.layout.fragment_received, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        mPresenter.getProjectConsumes(getProject().get_id());
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
        Log.d("CALLHHHH","CALL updateItem: ");
        adapter.update(adapter.getPosition(consume));
    }

    @Override
    public void removeFromAdapter(Consume consume) {

    }

    @Override
    public void onImageClick(int position) {

    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemUpdate(int position) {
        get_activity().startUpdateConsumeActivity(adapter.getItem(position));
    }

    @Override
    public void onItemRemove(int position) {

    }
}
