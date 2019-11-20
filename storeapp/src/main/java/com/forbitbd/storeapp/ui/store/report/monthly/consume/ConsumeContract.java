package com.forbitbd.storeapp.ui.store.report.monthly.consume;

import com.forbitbd.storeapp.models.Consume;

import java.util.List;

public interface ConsumeContract {

    interface Presenter{
        void process(List<Consume> consumeList);
    }

    interface View{
        void clearAdapter();
        void addItem(Consume consume);
    }
}
