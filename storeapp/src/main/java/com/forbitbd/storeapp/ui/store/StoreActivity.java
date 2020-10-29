package com.forbitbd.storeapp.ui.store;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.forbitbd.androidutils.models.Consume;
import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.utils.PrebaseActivity;
import com.forbitbd.androidutils.utils.ViewPagerAdapter;
import com.forbitbd.storeapp.R;
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
import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class StoreActivity extends PrebaseActivity implements StoreContract.View, View.OnClickListener {

    private StorePresenter mPresenter;

    private static final int ADD_SUPPLIER=8000;
    private static final int UPDATE_SUPPLIER=10000;
    private static final int RECEIVED=9000;
    private static final int RECEIVED_UPDATE=11000;
    private static final int CONSUMED=12000;
    private static final int CONSUMED_UPDATE=13000;
    private static final int READ_WRITE_PERMISSION=12000;

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
        setupToolbar(R.id.toolbar);
        getSupportActionBar().setTitle(project.getName().concat(" | Store"));

        setupBannerAd(R.id.adView);

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

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeQueryText(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void changeQueryText(int position){
        switch (position){
            case 0:
                searchView.setQueryHint("Search Supplier by Name");
                break;

            case 1:
                searchView.setQueryHint("Search By Name or Invoice");
                break;

            case 2:
                searchView.setQueryHint("Search By Name, Issue to, Where Used");
                break;
        }
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
    public void filter(String query) {
        switch (viewPager.getCurrentItem()){
            case 0:
                supplierFragment.filter(query);
                break;

            case 1:
                receivedFragment.filter(query);
                break;

            case 2:
                consumedFragment.filter(query);
                break;
        }

    }

    @Override
    public String saveFile(ResponseBody responseBody) {
        return saveTaskFile("Construction Manager",project.getName(),"Store","store.xlsx",responseBody);
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
            requestFileAfterPermission();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem searchMenuItem =  menu.findItem(R.id.search);

        Drawable drawable = searchMenuItem.getIcon();
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, ContextCompat.getColor(this,android.R.color.white));
        searchMenuItem.setIcon(drawable);

        searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setQueryHint("Search Supplier by Name");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPresenter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mPresenter.filter(newText);
                return false;
            }
        });


        return true;
    }

    @AfterPermissionGranted(READ_WRITE_PERMISSION)
    private void requestFileAfterPermission() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(getApplicationContext(), perms)) {
            //sendDownloadRequest();
            Log.d("UUUUUUUU","Called");
            mPresenter.downloadFile(project);
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "App need to Permission for Read and Write",
                    READ_WRITE_PERMISSION, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(
                requestCode, permissions, grantResults, this);
    }
}
