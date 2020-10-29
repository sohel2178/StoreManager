package com.forbitbd.storeapp.ui.store.report.monthly;

import com.forbitbd.androidutils.models.Consume;
import com.forbitbd.storeapp.models.Receive;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MonthlyPresenter implements MonthlyContract.Presenter {

    private MonthlyContract.View mView;

    public MonthlyPresenter(MonthlyContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void filterData(List<Receive> receiveList, List<Consume> consumeList, int currentMonth, int currentYear) {
        List<Receive> monthlyReceiveList = new ArrayList<>();
        List<Consume> monthlyConsumeList = new ArrayList<>();

        Calendar c = Calendar.getInstance();

        for (Receive x: receiveList){
            c.setTime(x.getDate());
            if(c.get(Calendar.YEAR)==currentYear && c.get(Calendar.MONTH)==currentMonth){
                monthlyReceiveList.add(x);
            }
        }

        for (Consume x: consumeList){
            c.setTime(x.getDate());
            if(c.get(Calendar.YEAR)==currentYear && c.get(Calendar.MONTH)==currentMonth){
                monthlyConsumeList.add(x);
            }
        }

        mView.sendDataToFragment(monthlyReceiveList,monthlyConsumeList);

    }
}
