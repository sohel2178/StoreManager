package com.forbitbd.storeapp.ui.store.report.monthly.consume;


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
import com.forbitbd.storeapp.models.Consume;
import com.forbitbd.storeapp.ui.store.report.monthly.MonthlyFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConsumeFragment extends Fragment implements ConsumeContract.View, BaseListener {

    private ConsumePresenter mPresenter;
    private ConsumeAdapter adapter;

    private MonthlyFragment parent;


    public ConsumeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ConsumePresenter(this);
        adapter = new ConsumeAdapter(getContext(),this);
        parent = (MonthlyFragment) getParentFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_receive, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        mPresenter.process(parent.getConsumeList());
    }

    public void update(){
        if(mPresenter!=null){
            mPresenter.process(parent.getConsumeList());
        }
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

    @Override
    public void clearAdapter() {
        adapter.clear();
    }

    @Override
    public void addItem(Consume consume) {
        adapter.add(consume);
    }
}
