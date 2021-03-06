package com.forbitbd.storeapp.ui.store.report.monthly.summery;


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
import com.forbitbd.storeapp.models.ReportSummery;
import com.forbitbd.storeapp.ui.store.report.daily.DailyAdapter;
import com.forbitbd.storeapp.ui.store.report.daily.DailyContract;
import com.forbitbd.storeapp.ui.store.report.daily.DailyPresenter;
import com.forbitbd.storeapp.ui.store.report.monthly.MonthlyFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MonthlySummeryFragment extends Fragment implements BaseListener, DailyContract.View {

    private DailyAdapter adapter;
    private DailyPresenter mPresenter;

    private MonthlyFragment parent;


    public MonthlySummeryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new DailyPresenter(this);
        adapter = new DailyAdapter(getContext(),this);

        if(getParentFragment() instanceof MonthlyFragment){
            parent = (MonthlyFragment) getParentFragment();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_summery3, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        mPresenter.processData(parent.getReceiveList(),parent.getConsumeList());

    }

    public void update(){
        if(mPresenter!=null){
            mPresenter.processData(parent.getReceiveList(),parent.getConsumeList());
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
    public void openCalendar() {

    }

    @Override
    public void addToAdapter(ReportSummery reportSummery) {
        adapter.add(reportSummery);
    }
}
