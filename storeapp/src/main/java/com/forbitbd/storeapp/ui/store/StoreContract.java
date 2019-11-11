package com.forbitbd.storeapp.ui.store;

import com.forbitbd.storeapp.models.Supplier;

public interface StoreContract {

    interface Presenter{
        void startAddSupplierActivity();
        void startReceivedActivity();
    }

    interface View{
        void startAddSupplierActivity();
        void startReceivedActivity();
        void startUpdateSupplierActivity(Supplier supplier);
    }
}
