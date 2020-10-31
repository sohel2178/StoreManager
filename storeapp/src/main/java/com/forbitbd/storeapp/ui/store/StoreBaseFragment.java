package com.forbitbd.storeapp.ui.store;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.models.SharedProject;

public abstract class StoreBaseFragment extends Fragment {

    private StoreActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getActivity() instanceof StoreActivity){
            this.activity = (StoreActivity) getActivity();
        }
    }


    public StoreActivity get_activity(){
        return this.activity;
    }

    public Project getProject(){
        return activity.getProject();
    }

    public SharedProject getSharedProject(){
        return activity.getSharedProject();
    }

    public void showProgressDialog(){
        activity.showProgressDialog();
    }

    public void hideProgressDialog(){
        activity.hideProgressDialog();
    }
}
