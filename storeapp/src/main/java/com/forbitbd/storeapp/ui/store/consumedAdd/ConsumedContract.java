package com.forbitbd.storeapp.ui.store.consumedAdd;

import com.forbitbd.storeapp.models.Consume;
import com.forbitbd.storeapp.models.Receive;

public interface ConsumedContract {

    interface Presenter{
        void bind();
        void openCalendar();
        void checkBeforeSave();
        boolean validate(Consume consume);
        void openCamera();
        void saveConsume(Consume consume,byte[] bytes);
        void updateConsume(Consume consume,byte[] bytes);
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
        void complete(Consume consume);
    }
}
