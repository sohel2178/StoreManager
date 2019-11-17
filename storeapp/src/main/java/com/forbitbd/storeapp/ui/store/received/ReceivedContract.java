package com.forbitbd.storeapp.ui.store.received;

import com.forbitbd.storeapp.models.Receive;
import com.forbitbd.storeapp.models.Supplier;

import java.util.List;

public interface ReceivedContract {

    interface Presenter{
        void getProjectReceives(String projectID);
        void deleteReceive(Receive receive);
    }

    interface View{
        List<String> getUnits();
        List<String> getMaterialNames();
        void renderAdapter(List<Receive> receiveList);
        void showProgressDialog();
        void hideProgressDialog();
        void addItem(Receive receive);
        void updateItem(Receive receive);
        void removeFromAdapter(Receive receive);
        void removeSupplierReceive(Supplier supplier);
    }
}
