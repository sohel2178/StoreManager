package com.forbitbd.storeapp.ui.store.report.monthly;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.forbitbd.androidutils.utils.ViewPagerAdapter;
import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.models.Consume;
import com.forbitbd.storeapp.models.Receive;
import com.forbitbd.storeapp.ui.store.report.ReportBase;
import com.forbitbd.storeapp.ui.store.report.monthly.consume.ConsumeFragment;
import com.forbitbd.storeapp.ui.store.report.monthly.received.ReceiveFragment;
import com.forbitbd.storeapp.ui.store.report.monthly.summery.MonthlySummeryFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MonthlyFragment extends ReportBase implements View.OnClickListener, MonthlyContract.View {

    private List<Receive> receiveList;
    private List<Consume> consumeList;


    private MonthlySummeryFragment summeryFragment;
    private ReceiveFragment receiveFragment;
    private ConsumeFragment consumeFragment;

    TextView tvPrev,tvNext,tvStatus;
    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;
    private TabLayout tabLayout;


    private int currentMonth,currentYear;

    private MonthlyPresenter mPresenter;




    public MonthlyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.summeryFragment = new MonthlySummeryFragment();
        this.receiveFragment = new ReceiveFragment();
        this.consumeFragment = new ConsumeFragment();

        this.currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        this.currentYear = Calendar.getInstance().get(Calendar.YEAR);

        mPresenter = new MonthlyPresenter(this);


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

        tvStatus = view.findViewById(R.id.status);
        tvPrev = view.findViewById(R.id.prev);
        tvNext = view.findViewById(R.id.next);

        viewPager = view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        tvPrev.setOnClickListener(this);
        tvNext.setOnClickListener(this);

        tvStatus.setText(getStringDate());

        mPresenter.filterData(get_activity().getReceiveList(),get_activity().getConsumeList(),currentMonth,currentYear);




    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void setupViewPager(ViewPager viewPager) {

        if(pagerAdapter==null){
            pagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        }else {
            pagerAdapter.clear();
        }
        pagerAdapter.addFragment(summeryFragment,"Summery");
        pagerAdapter.addFragment(receiveFragment,"Received");
        pagerAdapter.addFragment(consumeFragment,"Consumed");

        viewPager.setAdapter(pagerAdapter);

    }

    @Override
    public void onClick(View v) {
        if(v==tvPrev){
            decrease();
            mPresenter.filterData(get_activity().getReceiveList(),get_activity().getConsumeList(),currentMonth,currentYear);

        }else if(v==tvNext){
            increase();
            mPresenter.filterData(get_activity().getReceiveList(),get_activity().getConsumeList(),currentMonth,currentYear);
        }
    }


    private String getStringDate(){
        String month = getResources().getStringArray(R.array.month_array)[currentMonth];
        return month+" - "+currentYear;
    }

    private void increase(){
        currentMonth++;
        if(currentMonth>11){
            currentYear++;
            currentMonth= currentMonth%12;
        }
    }

    private void decrease(){
        currentMonth--;
        if(currentMonth<0){
            currentYear--;
            currentMonth= currentMonth+12;
        }
    }

    @Override
    public void sendDataToFragment(List<Receive> receiveList, List<Consume> consumeList) {
        tvStatus.setText(getStringDate());
        this.receiveList = receiveList;
        this.consumeList = consumeList;
        summeryFragment.update();
        receiveFragment.update();
        consumeFragment.update();
    }

    public List<Receive> getReceiveList(){
        return this.receiveList;
    }

    public List<Consume> getConsumeList(){
        return this.consumeList;
    }
}
