package com.forbitbd.storeapp.ui.store.report.chart;

import com.forbitbd.storeapp.models.Consume;
import com.forbitbd.storeapp.models.Receive;

import java.util.Date;
import java.util.List;


public interface ChartContract {

    interface Presenter{
        void generateNameArray(List<Receive> receiveList, List<Consume> consumeList);
        void filter(String name,List<Receive> receiveList,List<Consume> consumeList);
    }

    interface View{
        void updateMaterialAdapter(List<String> nameList);
        void updateChart(List<Date> dateList,List<Float> receiveList,List<Float> consumeList);
    }
}
