package com.forbitbd.storeapp.ui.store.report;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class ReportBase extends Fragment {

    private ReportActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getActivity() instanceof ReportActivity){
            this.activity = (ReportActivity) getActivity();
        }
    }


    public ReportActivity get_activity(){
        return this.activity;
    }

    public void showProgressDialog(){
        activity.showProgressDialog();
    }

    public void hideProgressDialog(){
        activity.hideProgressDialog();
    }
}
