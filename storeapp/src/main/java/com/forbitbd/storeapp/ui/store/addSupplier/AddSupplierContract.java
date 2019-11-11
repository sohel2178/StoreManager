package com.forbitbd.storeapp.ui.store.addSupplier;


import com.forbitbd.storeapp.models.Supplier;

public interface AddSupplierContract {

    interface Presenter{
        void openCamera();
        boolean validate(Supplier supplier);
        void saveSupplier(Supplier supplier, byte[] bytes);
        void updateSupplier(Supplier supplier, byte[] bytes);
        void bind();
    }

    interface View{
        void openCamera();
        void clearPreError();
        void showValidationError(String message, int fieldId);
        void showProgressDialog();
        void hideProgressDialog();
        void addSupplierComplete(Supplier supplier);
        void updateSupplierComplete(Supplier supplier);

        void bind();
    }
}
