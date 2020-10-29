package com.forbitbd.storeapp.ui.store.report.monthly;

import com.forbitbd.androidutils.models.Consume;
import com.forbitbd.storeapp.models.Receive;

import java.util.List;

public interface MonthlyContract {

    interface Presenter{
        void filterData(List<Receive> receiveList, List<Consume> consumeList, int currentMonth, int currentYear);
    }

    interface View{
        void sendDataToFragment(List<Receive> receiveList, List<Consume> consumeList);
    }
}
