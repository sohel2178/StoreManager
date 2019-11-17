package com.forbitbd.storeapp.ui.store.supplier.supplier_detail;


import android.os.Bundle;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.models.Receive;
import com.forbitbd.storeapp.models.Supplier;
import com.forbitbd.storeapp.ui.store.supplier.supplier_detail.history.HistoryFragment;
import com.forbitbd.storeapp.ui.store.supplier.supplier_detail.summery.SummeryFragment;
import com.forbitbd.storeapp.utils.Constant;
import com.forbitbd.storeapp.utils.PrebaseActivity;
import com.forbitbd.storeapp.utils.ViewPagerAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_detail);
        this.supplier = (Supplier) getIntent().getSerializableExtra(Constant.SUPPLIER);

        mPresenter = new SupplierDetailPresenter(this);

        initView();
    }

    private void initView() {
        setupToolbar();
        getSupportActionBar().setTitle(supplier.getName().concat(" Details"));

        tvTransactionCount = findViewById(R.id.transaction_count);

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        mPresenter.getSupplierReceive(supplier);
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