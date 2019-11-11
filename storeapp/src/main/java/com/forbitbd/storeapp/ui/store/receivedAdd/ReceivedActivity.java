package com.forbitbd.storeapp.ui.store.receivedAdd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.models.Project;
import com.forbitbd.storeapp.models.Receive;
import com.forbitbd.storeapp.models.Supplier;
import com.forbitbd.storeapp.utils.Constant;
import com.forbitbd.storeapp.utils.PrebaseActivity;

import java.util.List;

public class ReceivedActivity extends PrebaseActivity implements ReceivedContract.View {

    private ReceivedPresenter mPresenter;

    private Project project;
    private Receive receive;
    private List<Supplier> supplierList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received);
        mPresenter = new ReceivedPresenter(this);
        this.project = (Project) getIntent().getSerializableExtra(Constant.PROJECT);
        this.receive = (Receive) getIntent().getSerializableExtra(Constant.RECEIVED);
        this.supplierList = (List<Supplier>) getIntent().getSerializableExtra(Constant.SUPPLIER_LIST);

        Log.d("DDDDDD","Supplier Size "+supplierList.size());

        initView();
    }

    private void initView() {
        setupToolbar();
        getSupportActionBar().setTitle("Material Received Form");


    }
}
