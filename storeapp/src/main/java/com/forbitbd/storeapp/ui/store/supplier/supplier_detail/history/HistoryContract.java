package com.forbitbd.storeapp.ui.store.supplier.supplier_detail.history;

import com.forbitbd.storeapp.models.Receive;
import com.forbitbd.storeapp.models.Summery;

import java.util.List;

public interface HistoryContract {

    interface Presenter{


        void deleteReceive(Receive receive);


    }

    interface View{
        void showProgressDialog();
        void hideProgressDialog();


        void removeFromAdapter(Receive receive);
    }
}
