package com.forbitbd.storeapp.ui.store.supplier;



import com.forbitbd.storeapp.models.Supplier;

import java.util.List;

public interface SupplierContract {

    interface Presenter{
        void getProjectSuppliers(String projectID);
    }

    interface View{
        void renderAdapter(List<Supplier> supplierList);
        void showProgressDialog();
        void hideProgressDialog();
    }
}
