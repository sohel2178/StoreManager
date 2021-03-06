package com.forbitbd.storeapp.ui.store.report.summery;

import com.forbitbd.androidutils.models.Consume;
import com.forbitbd.storeapp.models.Receive;
import com.forbitbd.storeapp.models.ReportSummery;

import java.util.List;

public interface SummeryContract {
    interface Presenter{
        void process(List<Receive> receiveList, List<Consume> consumeList);
    }

    interface View{
        void clearAdapter();
        void addToadapter(ReportSummery reportSummery);
    }
}
