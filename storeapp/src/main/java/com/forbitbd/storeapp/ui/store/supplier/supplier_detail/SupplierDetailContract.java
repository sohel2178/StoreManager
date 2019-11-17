package com.forbitbd.storeapp.ui.store.supplier.supplier_detail;

import com.forbitbd.storeapp.models.Receive;
import com.forbitbd.storeapp.models.Supplier;

import java.util.List;

public interface SupplierDetailContract {

    interface Presenter{

        void getSupplierReceive(Supplier supplier);

    }

    interface View{
        void showProgressDialog();
        void hideProgressDialog();
        void update(List<Receive> receiveList);
    }
}
