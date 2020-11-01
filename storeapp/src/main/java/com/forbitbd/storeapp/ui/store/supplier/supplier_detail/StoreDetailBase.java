package com.forbitbd.storeapp.ui.store.supplier.supplier_detail;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.forbitbd.androidutils.models.SharedProject;
import com.forbitbd.storeapp.models.Receive;

import java.util.List;

public abstract class StoreDetailBase extends Fragment {

    private SupplierDetailActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = (SupplierDetailActivity) getActivity();
    }


    public SupplierDetailActivity get_activity(){
        return this.activity;
    }

    public SharedProject getSharedProject(){
        return activity.getSharedProject();
    }

    public abstract void update(List<Receive> receiveList);

    public void showProgressDialog(){
        activity.showProgressDialog();
    }

    public void hideProgressDialog(){
        activity.hideProgressDialog();
    }
}
