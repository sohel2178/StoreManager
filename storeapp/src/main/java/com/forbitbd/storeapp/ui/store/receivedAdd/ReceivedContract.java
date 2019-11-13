package com.forbitbd.storeapp.ui.store.receivedAdd;

import com.forbitbd.storeapp.models.Receive;

public interface ReceivedContract {

    interface Presenter{
        void bind();
        void openCalendar();
        void checkBeforeSave();
        boolean validate(Receive receive);
        void openCamera();

        void saveReceive(Receive receive,byte[] bytes);
        void updateReceive(Receive receive,byte[] bytes);
    }

    interface View{
        void showProgressDialog();
        void hideProgressDialog();
        void bind();
        void checkBeforeSave();
        void openCalendar();
        void openCamera();
        void clearPreError();
        void showToast(String message);
        void showError(String message,int fieldId);
        void complete(Receive receive);
    }
}
