package com.forbitbd.storeapp.ui.store.comsumed;

import com.forbitbd.storeapp.models.Consume;
import com.forbitbd.storeapp.models.Receive;

import java.util.List;

public interface ConsumedContract {

    interface Presenter{
        void getProjectConsumes(String projectID);
        void deleteConsume(Consume receive);

    }

    interface View{
        void renderAdapter(List<Consume> consumeList);
        void showProgressDialog();
        void hideProgressDialog();
        void addItem(Consume receive);
        void updateItem(Consume receive);
        void removeFromAdapter(Consume receive);

    }
}
