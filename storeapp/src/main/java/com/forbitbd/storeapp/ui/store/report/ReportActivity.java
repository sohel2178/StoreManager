package com.forbitbd.storeapp.ui.store.report;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.models.Consume;
import com.forbitbd.storeapp.models.Project;
import com.forbitbd.storeapp.models.Receive;
import com.forbitbd.storeapp.models.StoreResponse;
import com.forbitbd.storeapp.models.Supplier;
import com.forbitbd.storeapp.ui.store.report.daily.DailyFragment;
import com.forbitbd.storeapp.ui.store.report.monthly.MonthlyFragment;
import com.forbitbd.storeapp.ui.store.report.summery.SummeryFragment;
import com.forbitbd.storeapp.utils.Constant;
import com.forbitbd.storeapp.utils.PrebaseActivity;
import com.forbitbd.storeapp.utils.ViewPagerAdapter;

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

    private String[] titleArray = {"Summery","Daily Transaction","Monthly Transaction"};

    private SummeryFragment summeryFragment;
    private DailyFragment dailyFragment;
    private MonthlyFragment monthlyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        this.project = (Project) getIntent().getSerializableExtra(Constant.PROJECT);
        mPresenter = new ReportPresenter(this);

        initView();
    }

    private void initView() {
        setupToolbar();
        getSupportActionBar().setTitle(project.getName().concat(" Store Report"));

        this.summeryFragment = new SummeryFragment();
        this.dailyFragment = new DailyFragment();
        this.monthlyFragment = new MonthlyFragment();

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
        }
        pagerAdapter.addFragment(summeryFragment,"Summery");
        pagerAdapter.addFragment(dailyFragment,"Daily Transaction");
        pagerAdapter.addFragment(monthlyFragment,"Monthly Transaction");
//        pagerAdapter.addFragment(new TrialBalanceFragment(), "Trial Balance");
//        pagerAdapter.addFragment(new DailyTransactionFragment(), "Daily Transaction");
//        pagerAdapter.addFragment(new MonthlyTransactionFragment(), "Monthly Transaction");
//        pagerAdapter.addFragment(new CashFlowFragment(), "Cash Flow");
        //pagerAdapter.addFragment(new TransactionFragment(), "Transactions");

        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onClick(View view) {
        if(view==tvNext){

        }else if(view==tvPrev){

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

    }
}
