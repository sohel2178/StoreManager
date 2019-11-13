package com.forbitbd.storeapp.ui.store.receivedAdd;

import com.forbitbd.storeapp.models.Receive;

public interface ReceivedContract {

    interface Presenter{
        void bind();
        void openCalendar();
        boolean validate(Receive receive);
        void openCamera();
    }

    interface View{
        void showProgressDialog();
        void hideProgressDialog();
        void bind();
        void openCalendar();
        void openCamera();
        void clearPreError();
        void showToast(String message);
        void showError(String message,int fieldId);
    }
}
