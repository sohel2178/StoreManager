package com.forbitbd.storeapp.ui.store.supplier.supplier_detail.summery;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.baseAdapter.BaseListener;
import com.forbitbd.storeapp.models.Receive;
import com.forbitbd.storeapp.models.Summery;
import com.forbitbd.storeapp.ui.store.supplier.supplier_detail.StoreDetailBase;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SummeryFragment extends StoreDetailBase implements SummeryContract.View, BaseListener {

    private SummeryAdapter adapter;
    private SummeryPresenter mPresenter;

    public SummeryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new SummeryAdapter(getContext(),this);
        mPresenter = new SummeryPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_summery, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void addToAdapter(Summery summery) {
        adapter.add(summery);
    }

    @Override
    public void update(List<Receive> receiveList) {
        mPresenter.groupByName(receiveList);
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemUpdate(int position) {

    }

    @Override
    public void onItemRemove(int position) {

    }
}
