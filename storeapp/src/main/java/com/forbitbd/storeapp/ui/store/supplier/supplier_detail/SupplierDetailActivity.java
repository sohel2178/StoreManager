package com.forbitbd.storeapp.ui.store.supplier.supplier_detail;


import android.os.Bundle;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.forbitbd.androidutils.models.SharedProject;
import com.forbitbd.androidutils.utils.AppPreference;
import com.forbitbd.androidutils.utils.PrebaseActivity;
import com.forbitbd.androidutils.utils.ViewPagerAdapter;
import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.models.Receive;
import com.forbitbd.storeapp.models.Supplier;
import com.forbitbd.storeapp.ui.store.supplier.supplier_detail.history.HistoryFragment;
import com.forbitbd.storeapp.ui.store.supplier.supplier_detail.summery.SummeryFragment;
import com.forbitbd.storeapp.utils.Constant;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class SupplierDetailActivity extends PrebaseActivity implements SupplierDetailContract.View {

    private SupplierDetailPresenter mPresenter;

    private Supplier supplier;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private TextView tvTransactionCount;

    private HistoryFragment historyFragment;
    private SummeryFragment summeryFragment;

    private SharedProject sharedProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_detail);
        this.supplier = (Supplier) getIntent().getSerializableExtra(Constant.SUPPLIER);
        this.sharedProject = (SharedProject) getIntent().getSerializableExtra(Constant.PROJECT);

        mPresenter = new SupplierDetailPresenter(this);

        initView();
    }

    private void initView() {
        setupToolbar(R.id.toolbar);
        getSupportActionBar().setTitle(supplier.getName().concat(" Details"));

        setupBannerAd(R.id.adView);

        tvTransactionCount = findViewById(R.id.transaction_count);

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        mPresenter.getSupplierReceive(supplier);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(AppPreference.getInstance(this).getCounter()> com.forbitbd.androidutils.utils.Constant.COUNTER){
            showInterAd();
        }
    }

    public SharedProject getSharedProject(){
        return this.sharedProject;
    }

    private void setupViewPager(ViewPager viewPager) {
        if(adapter==null){
            adapter = new ViewPagerAdapter(getSupportFragmentManager());
        }
        summeryFragment = new SummeryFragment();
        historyFragment = new HistoryFragment();
        adapter.addFragment(summeryFragment, "SUMMERY");
        adapter.addFragment(historyFragment, "HISTORY");
        //adapter.addFragment(new ThreeFragment(), "THREE");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void update(List<Receive> receiveList) {
        historyFragment.update(receiveList);
        summeryFragment.update(receiveList);

        tvTransactionCount.setText(receiveList.size()+" Nos");
    }
}
