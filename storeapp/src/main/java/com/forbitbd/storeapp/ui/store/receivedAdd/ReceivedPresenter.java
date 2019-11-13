package com.forbitbd.storeapp.ui.store.receivedAdd;

import com.forbitbd.storeapp.models.Receive;

public class ReceivedPresenter implements ReceivedContract.Presenter {

    private ReceivedContract.View mView;

    public ReceivedPresenter(ReceivedContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void bind() {
        mView.bind();
    }

    @Override
    public void openCalendar() {
        mView.openCalendar();
    }

    @Override
    public boolean validate(Receive receive) {
        mView.clearPreError();
        if(receive.getReceived_from()==null){
            mView.showToast("Please Select a Supplier");
            return false;
        }

        if(receive.getDate()==null){
            mView.showToast("Please Select Received date");
            return false;
        }

        if(receive.getInvoice_no().equals("")){
            mView.showError("Invoice Number Should not Empty",1);
            return false;
        }

        if(receive.getName().equals("")){
            mView.showError("Material Name Should not Empty",2);
            return false;
        }

        if(receive.getUnit().equals("")){
            mView.showError("Material Unit Should not Empty",3);
            return false;
        }

        if(receive.getAmount() <=0){
            mView.showError("Amount Should be Greater than 0",4);
            return false;
        }
        return true;
    }

    @Override
    public void openCamera() {
        mView.openCamera();
    }
}
