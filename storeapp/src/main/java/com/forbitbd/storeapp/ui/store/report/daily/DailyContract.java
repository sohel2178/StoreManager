package com.forbitbd.storeapp.ui.store.report.daily;

import com.forbitbd.storeapp.models.Consume;
import com.forbitbd.storeapp.models.Receive;
import com.forbitbd.storeapp.models.ReportSummery;

import java.util.Date;
import java.util.List;

public interface DailyContract {

    interface Presenter{
        void openCalendar();
        void process(Date date, List<Receive> receiveList, List<Consume> consumeList);
    }

    interface View{
        void openCalendar();
        void addToAdapter(ReportSummery reportSummery);
    }
}
