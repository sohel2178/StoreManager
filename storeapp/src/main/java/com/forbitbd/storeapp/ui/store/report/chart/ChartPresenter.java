package com.forbitbd.storeapp.ui.store.report.chart;




import com.forbitbd.androidutils.models.Consume;
import com.forbitbd.androidutils.utils.MyUtil;
import com.forbitbd.storeapp.models.Receive;

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

        Date actualStartDate,actualEndDate,receivedStartDate,receivedEndDate,consumeEndDate,consumeStartDate;


        if(filteredReceiveList.size()==0 && filteredConsumeList.size()==0){
            return;
        }else if(filteredReceiveList.size()!=0 && filteredConsumeList.size()==0){
            receivedStartDate = filteredReceiveList.get(filteredReceiveList.size()-1).getDate();
            receivedEndDate = filteredReceiveList.get(0).getDate();

            consumeEndDate = receivedEndDate;
            consumeStartDate = receivedStartDate;
        }else if(filteredReceiveList.size()==0 && filteredConsumeList.size()!=0){
            consumeEndDate = filteredConsumeList.get(0).getDate();
            consumeStartDate = filteredConsumeList.get(filteredConsumeList.size()-1).getDate();
            receivedStartDate = consumeStartDate;
            receivedEndDate = consumeEndDate;
        }else{
             receivedStartDate = filteredReceiveList.get(filteredReceiveList.size()-1).getDate();
             receivedEndDate = filteredReceiveList.get(0).getDate();
             consumeEndDate = filteredConsumeList.get(0).getDate();
             consumeStartDate = filteredConsumeList.get(filteredConsumeList.size()-1).getDate();
        }


        actualStartDate = new Date(Math.min(receivedStartDate.getTime(),consumeStartDate.getTime()));
        actualEndDate = new Date(Math.max(receivedEndDate.getTime(),consumeEndDate.getTime()));

        int duration = MyUtil.getDuration(actualEndDate.getTime(),actualStartDate.getTime());
        Calendar cal = Calendar.getInstance();
        cal.setTime (actualStartDate);

        List<Float> rList = new ArrayList<>();
        List<Float> cList = new ArrayList<>();
        List<Date> dateList = new ArrayList<>();

        for (int i=0;i<duration;i++){
            float receive =0;
            float consume =0;

            if(i!=0){
                cal.add(Calendar.DATE,1);
                receive = rList.get(i-1);
                consume = cList.get(i-1);
            }

            dateList.add(cal.getTime());

            for (Receive x: filteredReceiveList){
                if(MyUtil.getStringDate(x.getDate()).equals(MyUtil.getStringDate(cal.getTime()))){
                    receive = (float) (receive+x.getQuantity());
                }
            }

            for (Consume x: filteredConsumeList){
                if(MyUtil.getStringDate(x.getDate()).equals(MyUtil.getStringDate(cal.getTime()))){
                    consume = (float) (consume+x.getQuantity());
                }
            }

            rList.add(receive);
            cList.add(consume);
        }

        mView.updateChart(dateList,rList,cList);

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
