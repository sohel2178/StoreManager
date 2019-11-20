package com.forbitbd.storeapp.ui.store;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.models.Consume;
import com.forbitbd.storeapp.models.Project;
import com.forbitbd.storeapp.models.Receive;
import com.forbitbd.storeapp.models.Supplier;
import com.forbitbd.storeapp.ui.store.addSupplier.AddSupplierActivity;
import com.forbitbd.storeapp.ui.store.comsumed.ConsumedFragment;
import com.forbitbd.storeapp.ui.store.consumedAdd.ConsumedActivity;
import com.forbitbd.storeapp.ui.store.received.ReceivedFragment;
import com.forbitbd.storeapp.ui.store.receivedAdd.ReceivedActivity;
import com.forbitbd.storeapp.ui.store.report.ReportActivity;
import com.forbitbd.storeapp.ui.store.supplier.SupplierFragment;
import com.forbitbd.storeapp.utils.Constant;
import com.forbitbd.storeapp.utils.PrebaseActivity;
import com.forbitbd.storeapp.utils.ViewPagerAdapter;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StoreActivity extends PrebaseActivity implements StoreContract.View, View.OnClickListener {

    private StorePresenter mPresenter;

    private static final int ADD_SUPPLIER=8000;
    private static final int UPDATE_SUPPLIER=10000;
    private static final int RECEIVED=9000;
    private static final int RECEIVED_UPDATE=11000;
    private static final int CONSUMED=12000;
    private static final int CONSUMED_UPDATE=13000;

    private Project project;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;
    private SearchView searchView;

    private SupplierFragment supplierFragment;
    private ConsumedFragment consumedFragment;
    private ReceivedFragment receivedFragment;

    private FloatingActionButton fabCreateSupplier,fabReceived,fabConsumed,fabReport,fabDownload;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        mPresenter = new StorePresenter(this);
        this.project = (Project) getIntent().getSerializableExtra(Constant.PROJECT);

        initView();

    }

    private void initView(){
        setupToolbar();
        getSupportActionBar().setTitle(project.getName().concat(" | Store"));

        supplierFragment = new SupplierFragment();
        consumedFragment = new ConsumedFragment();
        receivedFragment = new ReceivedFragment();

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        fabCreateSupplier = findViewById(R.id.fab_add_supplier);
        fabReceived = findViewById(R.id.fab_received);
        fabConsumed = findViewById(R.id.fab_consumed);
        fabReport = findViewById(R.id.fab_report);
        fabDownload = findViewById(R.id.fab_download);

        fabCreateSupplier.setOnClickListener(this);
        fabReceived.setOnClickListener(this);
        fabConsumed.setOnClickListener(this);
        fabReport.setOnClickListener(this);
        fabDownload.setOnClickListener(this);
    }

    private void setupViewPager(ViewPager viewPager) {

        if(pagerAdapter==null){
            pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        }else{
            pagerAdapter.clear();
        }
        pagerAdapter.addFragment(supplierFragment,"Suppliers");
        pagerAdapter.addFragment(receivedFragment,"Received");
        pagerAdapter.addFragment(consumedFragment,"Consumed");
       /* pagerAdapter.addFragment(new AccountFragment(), "Accounts");
        pagerAdapter.addFragment(new TransactionFragment(), "Transactions");*/

        viewPager.setAdapter(pagerAdapter);

    }


    public Project getProject(){
        return project;
    }

    @Override
    public void startAddSupplierActivity() {
        Intent intent = new Intent(getApplicationContext(), AddSupplierActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.PROJECT,project);
        intent.putExtras(bundle);

        startActivityForResult(intent,ADD_SUPPLIER);
    }

    @Override
    public void startReceivedActivity() {
        Intent intent = new Intent(getApplicationContext(), ReceivedActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.PROJECT,project);
        bundle.putSerializable(Constant.SUPPLIER_LIST, (Serializable) supplierFragment.getSuppliers());
        bundle.putStringArrayList(Constant.UNITS, (ArrayList<String>) receivedFragment.getUnits());
        bundle.putStringArrayList(Constant.NAMES, (ArrayList<String>) receivedFragment.getMaterialNames());
        intent.putExtras(bundle);
        startActivityForResult(intent,RECEIVED);
    }

    @Override
    public void startConsumedActivity() {
        Intent intent = new Intent(getApplicationContext(), ConsumedActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.PROJECT,project);
        bundle.putStringArrayList(Constant.UNITS, (ArrayList<String>) receivedFragment.getUnits());
        bundle.putStringArrayList(Constant.NAMES, (ArrayList<String>) receivedFragment.getMaterialNames());
        intent.putExtras(bundle);
        startActivityForResult(intent,CONSUMED);
    }

    @Override
    public void startReportActivity() {
        Intent intent = new Intent(this, ReportActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.PROJECT,project);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    @Override
    public void startUpdateSupplierActivity(Supplier supplier) {
        Intent intent = new Intent(getApplicationContext(), AddSupplierActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.PROJECT,project);
        bundle.putSerializable(Constant.SUPPLIER,supplier);
        intent.putExtras(bundle);

        startActivityForResult(intent,UPDATE_SUPPLIER);
    }

    @Override
    public void startUpdateReceiveActivity(Receive receive) {
        Intent intent = new Intent(getApplicationContext(), ReceivedActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.PROJECT,project);
        bundle.putSerializable(Constant.RECEIVED,receive);
        bundle.putSerializable(Constant.SUPPLIER_LIST, (Serializable) supplierFragment.getSuppliers());
        bundle.putStringArrayList(Constant.UNITS, (ArrayList<String>) receivedFragment.getUnits());
        bundle.putStringArrayList(Constant.NAMES, (ArrayList<String>) receivedFragment.getMaterialNames());
        intent.putExtras(bundle);
        startActivityForResult(intent,RECEIVED_UPDATE);

    }

    @Override
    public void startUpdateConsumeActivity(Consume consume) {
        Intent intent = new Intent(getApplicationContext(), ConsumedActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.PROJECT,project);
        bundle.putSerializable(Constant.CONSUME,consume);
        bundle.putStringArrayList(Constant.UNITS, (ArrayList<String>) receivedFragment.getUnits());
        bundle.putStringArrayList(Constant.NAMES, (ArrayList<String>) receivedFragment.getMaterialNames());
        intent.putExtras(bundle);
        startActivityForResult(intent,CONSUMED_UPDATE);
    }

    @Override
    public void removeSupplierReceive(Supplier supplier) {
        receivedFragment.removeSupplierReceive(supplier);
    }


    @Override
    public void onClick(View v) {
        if(v==fabCreateSupplier){
            mPresenter.startAddSupplierActivity();
        }else if(v==fabReceived){
            mPresenter.startReceivedActivity();
        }else if(v==fabConsumed){
            mPresenter.startConsumedActivity();
        }else if(v==fabReport){
            mPresenter.startReportActivity();
        }else if(v==fabDownload){
           // mPresenter.startConsumedActivity();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==ADD_SUPPLIER && resultCode==RESULT_OK){
            Supplier supplier = (Supplier) data.getSerializableExtra(Constant.SUPPLIER);
            supplierFragment.addSupplierToAdapter(supplier);
        }

        if(requestCode==UPDATE_SUPPLIER && resultCode==RESULT_OK){
            Supplier supplier = (Supplier) data.getSerializableExtra(Constant.SUPPLIER);
            supplierFragment.updateSupplierToAdapter(supplier);
        }

        if(requestCode==RECEIVED && resultCode == RESULT_OK){
            Receive receive = (Receive) data.getSerializableExtra(Constant.RECEIVED);
            receivedFragment.addItem(receive);
        }

        if(requestCode==RECEIVED_UPDATE && resultCode==RESULT_OK){
            Receive receive = (Receive) data.getSerializableExtra(Constant.RECEIVED);
            receivedFragment.updateItem(receive);
        }

        if(requestCode==CONSUMED && resultCode == RESULT_OK){
            Consume consume = (Consume) data.getSerializableExtra(Constant.CONSUME);
            consumedFragment.addItem(consume);
        }

        if(requestCode==CONSUMED_UPDATE && resultCode == RESULT_OK){
            Log.d("CALLHHHH","CALL OnActivityResult: ");
            Consume consume = (Consume) data.getSerializableExtra(Constant.CONSUME);
            consumedFragment.updateItem(consume);
        }


    }
}
