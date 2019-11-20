package com.forbitbd.storeapp.ui.store.report.chart;


import android.util.Log;

import com.forbitbd.storeapp.models.Consume;
import com.forbitbd.storeapp.models.LineData;
import com.forbitbd.storeapp.models.Receive;
import com.forbitbd.storeapp.utils.MyUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;


public class ChartPresenter implements ChartContract.Presenter {

    private ChartContract.View mView;

    public ChartPresenter(ChartContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void generateNameArray(List<Receive> receiveList, List<Consume> consumeList) {
        HashSet<String> set = new HashSet<>();

        for (Receive x: receiveList){
            set.add(x.getName());
        }

        for (Consume x: consumeList){
            set.add(x.getName());
        }

        mView.updateMaterialAdapter(new ArrayList<String>(set));
    }

    @Override
    public void filter(String name, List<Receive> receiveList,List<Consume> consumeList) {
        List<Receive> filteredReceiveList = getFilteredReceiveList(name,receiveList);
        List<Consume> filteredConsumeList = getFilteredConsumeList(name,consumeList);

        List<LineData> receivedDataList = new ArrayList<>();

        if(filteredReceiveList.size()>0){
            Date endDate = filteredReceiveList.get(0).getDate();
            Date startDate = filteredReceiveList.get(filteredReceiveList.size()-1).getDate();

            int duration = MyUtil.getDuration(endDate.getTime(),startDate.getTime());
            Calendar cal = Calendar.getInstance();
            cal.setTime (startDate);

            Log.d("DURATION","RECEIVE "+duration);

            for (int i=0;i<duration;i++){
                if(i==0){
                    LineData lineData = new LineData(startDate);

                    for (Receive x: receiveList){
                        if(MyUtil.getStringDate(x.getDate()).equals(MyUtil.getStringDate(lineData.getDate()))){
                            lineData.addValue((float) x.getQuantity());
                        }
                    }
                    receivedDataList.add(lineData);
                }else{
                    cal.add(Calendar.DATE,1);
                    LineData lineData = new LineData(cal.getTime());
                    lineData.setValue(receivedDataList.get(i-1).getValue());

                    for (Receive x: receiveList){
                        if(MyUtil.getStringDate(x.getDate()).equals(MyUtil.getStringDate(lineData.getDate()))){
                            lineData.addValue((float) x.getQuantity());
                        }
                    }

                    receivedDataList.add(lineData);
                }

            }

            if(receivedDataList.size()>0){
                mView.updateReceive(receivedDataList);
            }
        }


        List<LineData> consumeLineDataList = new ArrayList<>();

        if(filteredConsumeList.size()>0){
            Date eDate = filteredConsumeList.get(0).getDate();
            Date sDate = filteredConsumeList.get(filteredConsumeList.size()-1).getDate();

            int duration = MyUtil.getDuration(eDate.getTime(),sDate.getTime());
            Calendar cal = Calendar.getInstance();
            cal.setTime (sDate);

            Log.d("DURATION","CONSUME "+duration);

            for (int i=0;i<duration;i++){

                if(i==0){
                    LineData lineData = new LineData(sDate);

                    for (Consume x: consumeList){
                        if(MyUtil.getStringDate(x.getDate()).equals(MyUtil.getStringDate(lineData.getDate()))){
                            lineData.addValue((float) x.getQuantity());
                        }
                    }
                    consumeLineDataList.add(lineData);
                }else{
                    cal.add(Calendar.DATE,1);
                    LineData lineData = new LineData(cal.getTime());
                    lineData.setValue(consumeLineDataList.get(i-1).getValue());

                    for (Consume x: consumeList){
                        if(MyUtil.getStringDate(x.getDate()).equals(MyUtil.getStringDate(lineData.getDate()))){
                            lineData.addValue((float) x.getQuantity());
                        }
                    }

                    consumeLineDataList.add(lineData);

                }

            }

            if(consumeLineDataList.size()>0){
                mView.updateConsume(consumeLineDataList);
            }

        }

    }


    private List<Receive> getFilteredReceiveList(String name ,List<Receive> receiveList){
        List<Receive> tmpList = new ArrayList<>();
        for (Receive x:receiveList){
            if(x.getName().equals(name)){
                tmpList.add(x);
            }
        }

        return tmpList;
    }

    private List<Consume> getFilteredConsumeList(String name ,List<Consume> receiveList){
        List<Consume> tmpList = new ArrayList<>();
        for (Consume x:receiveList){
            if(x.getName().equals(name)){
                tmpList.add(x);
            }
        }

        return tmpList;
    }
}
