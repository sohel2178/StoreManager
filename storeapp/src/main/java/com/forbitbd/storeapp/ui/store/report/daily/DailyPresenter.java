package com.forbitbd.storeapp.ui.store.report.daily;

import com.forbitbd.storeapp.models.Consume;
import com.forbitbd.storeapp.models.Receive;
import com.forbitbd.storeapp.models.ReportSummery;
import com.forbitbd.storeapp.utils.MyUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class DailyPresenter implements DailyContract.Presenter {

    private DailyContract.View mView;

    public DailyPresenter(DailyContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void openCalendar() {
        mView.openCalendar();
    }

    @Override
    public void process(Date date, List<Receive> receiveList, List<Consume> consumeList) {
        mView.clearAdapter();
        List<Receive> filterReceivedList = getFilteredReceivedList(date,receiveList);
        List<Consume> filterConsumedList = getFilteredConsumedList(date,consumeList);

        List<String> nameList = getnameList(filterReceivedList,filterConsumedList);

        for (String x: nameList){
            ReportSummery reportSummery = new ReportSummery();
            reportSummery.setItem(x);

            for (Receive y: filterReceivedList){
                if(y.getName().equals(x)){
                    reportSummery.addReceived(y.getQuantity());
                }
            }

            for (Consume z:filterConsumedList){
                if(z.getName().equals(x)){
                    reportSummery.addConsumed(z.getQuantity());
                }
            }

            mView.addToAdapter(reportSummery);

        }

    }

    @Override
    public void processData(List<Receive> receiveList, List<Consume> consumeList) {
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

            mView.addToAdapter(reportSummery);

        }
    }


    private List<Receive> getFilteredReceivedList(Date date,List<Receive> receiveList){
        List<Receive> tempList = new ArrayList<>();

        for (Receive x: receiveList){
            if(MyUtil.getStringDate(date).equals(MyUtil.getStringDate(x.getDate()))){
                tempList.add(x);
            }
        }

        return tempList;
    }

    private List<Consume> getFilteredConsumedList(Date date,List<Consume> consumeList){
        List<Consume> tempList = new ArrayList<>();

        for (Consume x: consumeList){
            if(MyUtil.getStringDate(date).equals(MyUtil.getStringDate(x.getDate()))){
                tempList.add(x);
            }
        }

        return tempList;
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
