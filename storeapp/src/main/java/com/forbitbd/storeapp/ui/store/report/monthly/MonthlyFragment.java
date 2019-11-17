package com.forbitbd.storeapp.ui.store.report.monthly;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.models.Consume;
import com.forbitbd.storeapp.models.Receive;
import com.forbitbd.storeapp.ui.store.report.ReportBase;
import com.forbitbd.storeapp.ui.store.report.monthly.summery.MonthlySummeryFragment;
import com.forbitbd.storeapp.ui.store.report.monthly.transaction.TransactionFragment;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MonthlyFragment extends ReportBase {

    private List<Receive> receiveList;
    private List<Consume> consumeList;


    private MonthlySummeryFragment summeryFragment;
    private TransactionFragment transactionFragment;


    public MonthlyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.receiveList = get_activity().getReceiveList();
        this.consumeList = get_activity().getConsumeList();
        this.summeryFragment = new MonthlySummeryFragment();
        this.transactionFragment = new TransactionFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_monthly, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {


    }

}
