package com.forbitbd.storeapp.ui.store.report.chart;

import com.forbitbd.storeapp.models.Consume;
import com.forbitbd.storeapp.models.LineData;
import com.forbitbd.storeapp.models.Receive;

import java.util.List;


public interface ChartContract {

    interface Presenter{
        void generateNameArray(List<Receive> receiveList, List<Consume> consumeList);
        void filter(String name,List<Receive> receiveList,List<Consume> consumeList);
    }

    interface View{
        void updateMaterialAdapter(List<String> nameList);
        void updateReceive(List<LineData> lineDataList);
        void updateConsume(List<LineData> lineDataList);
    }
}
