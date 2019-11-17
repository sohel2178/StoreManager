package com.forbitbd.storeapp.ui.store.supplier.supplier_detail.summery;

import com.forbitbd.storeapp.models.Receive;
import com.forbitbd.storeapp.models.Summery;

import java.util.List;

public interface SummeryContract {

    interface Presenter{
        void groupByName(List<Receive> receiveList);
    }

    interface View{
        void addToAdapter(Summery summery);
    }
}
