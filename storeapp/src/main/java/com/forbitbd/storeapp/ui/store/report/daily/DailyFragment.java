package com.forbitbd.storeapp.ui.store.report.daily;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.baseAdapter.BaseListener;
import com.forbitbd.storeapp.dialog.DatePickerListener;
import com.forbitbd.storeapp.dialog.MyDatePickerFragment;
import com.forbitbd.storeapp.models.ReportSummery;
import com.forbitbd.storeapp.ui.store.report.ReportBase;
import com.forbitbd.storeapp.utils.Constant;
import com.forbitbd.storeapp.utils.MyUtil;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class DailyFragment extends ReportBase implements DailyContract.View, View.OnClickListener, BaseListener {

    private DailyPresenter mPresenter;

    TextView tvDate;
    ImageView ivcalendar;

    private Date date;

    private DailyAdapter adapter;


    public DailyFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new DailyPresenter(this);
        adapter = new DailyAdapter(getContext(),this);

        date = new Date();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tvDate = view.findViewById(R.id.date);
        ivcalendar = view.findViewById(R.id.calender);



        ivcalendar.setOnClickListener(this);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        tvDate.setText("Transaction On date ".concat(MyUtil.getStringDate(date)));
        mPresenter.process(date,get_activity().getReceiveList(),get_activity().getConsumeList());



    }

    @Override
    public void onClick(View view) {
        if(view==ivcalendar){
            mPresenter.openCalendar();
        }
    }

    @Override
    public void clearAdapter() {
        adapter.clear();
    }

    @Override
    public void openCalendar() {
        MyDatePickerFragment myDateDialog = new MyDatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.TITLE,getString(R.string.select_transaction_date));
        myDateDialog.setArguments(bundle);
        myDateDialog.setCancelable(false);
        myDateDialog.setDatePickerListener(new DatePickerListener() {
            @Override
            public void onDatePick(long time) {
                date = new Date(time);
                adapter.clear();
                tvDate.setText("Transaction On date ".concat(MyUtil.getStringDate(date)));
                mPresenter.process(date,get_activity().getReceiveList(),get_activity().getConsumeList());

            }
        });
        myDateDialog.show(getChildFragmentManager(),"FFFF");
    }

    @Override
    public void addToAdapter(ReportSummery reportSummery) {
        adapter.add(reportSummery);
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
