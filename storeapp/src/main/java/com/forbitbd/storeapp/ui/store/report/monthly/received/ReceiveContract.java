package com.forbitbd.storeapp.ui.store.report.monthly.received;

import com.forbitbd.storeapp.models.Receive;

import java.util.List;

public interface ReceiveContract {

    interface Presenter{
        void process(List<Receive> receiveList);
    }

    interface View{
        void clearAdapter();
        void addItem(Receive receive);
    }
}
