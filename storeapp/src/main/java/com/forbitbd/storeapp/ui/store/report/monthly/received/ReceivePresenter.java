package com.forbitbd.storeapp.ui.store.report.monthly.received;

import com.forbitbd.storeapp.models.Receive;

import java.util.List;

public class ReceivePresenter implements ReceiveContract.Presenter {

    private ReceiveContract.View mView;

    public ReceivePresenter(ReceiveContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void process(List<Receive> receiveList) {
        mView.clearAdapter();

        for (Receive x: receiveList){
            mView.addItem(x);
        }
    }
}
