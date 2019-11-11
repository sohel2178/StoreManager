package com.forbitbd.storeapp.ui.store.receivedAdd;

public class ReceivedPresenter implements ReceivedContract.Presenter {

    private ReceivedContract.View mView;

    public ReceivedPresenter(ReceivedContract.View mView) {
        this.mView = mView;
    }
}
