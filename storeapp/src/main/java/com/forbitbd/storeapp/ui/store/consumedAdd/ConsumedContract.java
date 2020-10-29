package com.forbitbd.storeapp.ui.store.consumedAdd;

import com.forbitbd.androidutils.models.Consume;
import com.forbitbd.androidutils.models.Task;

import java.util.List;

public interface ConsumedContract {

    interface Presenter{
        void bind();
        void openCalendar();
        void checkBeforeSave();
        boolean validate(Consume consume);
        void openCamera();
        void saveConsume(Consume consume,byte[] bytes);
        void updateConsume(Consume consume,byte[] bytes);
        void getAllTask(String projectId);
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
        void updateTaskAdapter(List<Task> taskList);
    }
}
