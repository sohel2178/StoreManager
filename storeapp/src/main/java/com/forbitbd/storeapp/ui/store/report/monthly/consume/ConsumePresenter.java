package com.forbitbd.storeapp.ui.store.report.monthly.consume;

import com.forbitbd.storeapp.models.Consume;

import java.util.List;

public class ConsumePresenter implements ConsumeContract.Presenter {

    private ConsumeContract.View mView;

    public ConsumePresenter(ConsumeContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void process(List<Consume> consumeList) {
        mView.clearAdapter();

        for(Consume x: consumeList){
            mView.addItem(x);
        }
    }
}
