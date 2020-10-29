package com.forbitbd.storeapp.ui.store;

import com.forbitbd.androidutils.models.Consume;
import com.forbitbd.androidutils.models.Project;
import com.forbitbd.storeapp.models.Receive;
import com.forbitbd.storeapp.models.Supplier;

import okhttp3.ResponseBody;

public interface StoreContract {

    interface Presenter{
        void startAddSupplierActivity();
        void startReceivedActivity();
        void startConsumedActivity();
        void startReportActivity();
        void filter(String query);
        void downloadFile(Project project);
    }

    interface View{
        void startAddSupplierActivity();
        void startReceivedActivity();
        void startConsumedActivity();
        void startReportActivity();
        void startUpdateSupplierActivity(Supplier supplier);
        void startUpdateReceiveActivity(Receive receive);
        void startUpdateConsumeActivity(Consume consume);
        void removeSupplierReceive(Supplier supplier);
        void filter(String query);
        void showProgressDialog();
        void hideProgressDialog();
        String saveFile(ResponseBody responseBody);
        void openFile(String path);
    }
}
