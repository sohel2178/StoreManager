package com.forbitbd.storeapp.ui.store.report.summery;


import com.forbitbd.storeapp.models.Consume;
import com.forbitbd.storeapp.models.Receive;
import com.forbitbd.storeapp.models.ReportSummery;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SummeryPresenter implements SummeryContract.Presenter {

    private SummeryContract.View mView;

    public SummeryPresenter(SummeryContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void process(List<Receive> receiveList, List<Consume> consumeList) {

        mView.clearAdapter();

        for (String x: getnameList(receiveList,consumeList)){
            ReportSummery reportSummery = new ReportSummery();
            reportSummery.setItem(x);

            for (Receive y: receiveList){
                if(y.getName().equals(x)){
                    reportSummery.addReceived(y.getQuantity());
                }
            }

            for (Consume z:consumeList){
                if(z.getName().equals(x)){
                    reportSummery.addConsumed(z.getQuantity());
                }
            }

            mView.addToadapter(reportSummery);

        }

    }


    private List<String> getnameList(List<Receive> receiveList,List<Consume> consumeList){
        HashSet<String> set = new HashSet<>();

        for (Receive x: receiveList){
            set.add(x.getName());
        }

        for (Consume x: consumeList){
            set.add(x.getName());
        }
        return new ArrayList<>(set);
    }
}
