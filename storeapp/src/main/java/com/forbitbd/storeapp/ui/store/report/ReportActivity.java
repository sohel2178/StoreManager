package com.forbitbd.storeapp.ui.store.report;

import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.utils.PrebaseActivity;
import com.forbitbd.androidutils.utils.ViewPagerAdapter;
import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.models.Consume;
import com.forbitbd.storeapp.models.Receive;
import com.forbitbd.storeapp.models.StoreResponse;
import com.forbitbd.storeapp.models.Supplier;
import com.forbitbd.storeapp.ui.store.report.chart.ChartFragment;
import com.forbitbd.storeapp.ui.store.report.daily.DailyFragment;
import com.forbitbd.storeapp.ui.store.report.monthly.MonthlyFragment;
import com.forbitbd.storeapp.ui.store.report.summery.SummeryFragment;
import com.forbitbd.storeapp.utils.Constant;

import java.util.List;

public class ReportActivity extends PrebaseActivity implements ReportContract.View, View.OnClickListener {

    private ReportPresenter mPresenter;
    private Project project;

    TextView tvPrev,tvNext,tvStatus;
    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;

    private List<Supplier> supplierList;
    private List<Receive> receiveList;
    private List<Consume> consumeList;

    private String[] titleArray = {"Summery","Daily Transaction","Monthly Transaction","Receive-Consume Comparison"};

    private SummeryFragment summeryFragment;
    private DailyFragment dailyFragment;
    private MonthlyFragment monthlyFragment;
    private ChartFragment chartFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        this.project = (Project) getIntent().getSerializableExtra(Constant.PROJECT);
        mPresenter = new ReportPresenter(this);

        initView();
    }

    private void initView() {
        setupToolbar(R.id.toolbar);
        getSupportActionBar().setTitle(project.getName().concat(" Store Report"));

        this.summeryFragment = new SummeryFragment();
        this.dailyFragment = new DailyFragment();
        this.monthlyFragment = new MonthlyFragment();
        this.chartFragment = new ChartFragment();

        tvPrev = findViewById(R.id.prev);
        tvNext = findViewById(R.id.next);
        tvStatus = findViewById(R.id.status);

        tvPrev.setOnClickListener(this);
        tvNext.setOnClickListener(this);




        mPresenter.getStoreData(project.get_id());

    }

    private void setupViewPager(ViewPager viewPager) {
        if(pagerAdapter==null){
            pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        }else {
            pagerAdapter.clear();
        }
        pagerAdapter.addFragment(summeryFragment,"Summery");
        pagerAdapter.addFragment(dailyFragment,"Daily Transaction");
        pagerAdapter.addFragment(monthlyFragment,"Monthly Transaction");
        pagerAdapter.addFragment(chartFragment,"Receive-Consume Comparison");
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onClick(View view) {
        if(view==tvNext){
            if(viewPager.getCurrentItem()<pagerAdapter.getCount()-1){
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            }

        }else if(view==tvPrev){
            if(viewPager.getCurrentItem()!=0){
                viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
            }
        }
    }

    public List<Receive> getReceiveList(){
        return this.receiveList;
    }

    public List<Consume> getConsumeList(){
        return this.consumeList;
    }

    public List<Supplier> getSupplierList(){
        return this.supplierList;
    }

    @Override
    public void initializeData(StoreResponse storeResponse) {
        this.receiveList = storeResponse.getReceives();
        this.consumeList = storeResponse.getConsumes();
        this.supplierList = storeResponse.getSuppliers();

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvStatus.setText(titleArray[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        tvStatus.setText(titleArray[viewPager.getCurrentItem()]);

    }
}
