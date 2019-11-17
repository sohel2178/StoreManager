package com.forbitbd.storeapp.ui.store;

import com.forbitbd.storeapp.models.Consume;
import com.forbitbd.storeapp.models.Receive;
import com.forbitbd.storeapp.models.Supplier;

public interface StoreContract {

    interface Presenter{
        void startAddSupplierActivity();
        void startReceivedActivity();
        void startConsumedActivity();
        void startReportActivity();
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
    }
}
