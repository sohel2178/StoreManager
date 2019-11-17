package com.forbitbd.storeapp.ui.store.report;

import com.forbitbd.storeapp.models.StoreResponse;

public interface ReportContract {

    interface Presenter{
        void getStoreData(String projectID);
    }

    interface View{
        void showProgressDialog();
        void hideProgressDialog();
        void initializeData(StoreResponse storeResponse);
    }
}
