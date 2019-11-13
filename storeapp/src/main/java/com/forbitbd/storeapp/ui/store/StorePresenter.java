package com.forbitbd.storeapp.ui.store;

public class StorePresenter implements StoreContract.Presenter {

    private StoreContract.View mView;

    public StorePresenter(StoreContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void startAddSupplierActivity() {
        mView.startAddSupplierActivity();
    }

    @Override
    public void startReceivedActivity() {
        mView.startReceivedActivity();
    }

    @Override
    public void startConsumedActivity() {
        mView.startConsumedActivity();
    }
}
