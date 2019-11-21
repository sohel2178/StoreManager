package com.forbitbd.storeapp.ui.store.report.chart;


import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.ui.store.report.ReportBase;
import com.forbitbd.storeapp.utils.DateAxisFormatter;
import com.forbitbd.storeapp.utils.MyUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChartFragment extends ReportBase implements ChartContract.View {

    private ChartPresenter mPresenter;

    private AppCompatSpinner spMaterial;
    private ArrayAdapter<String> materialAdapter;

    private LineChart mReceiveChart;


    public ChartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ChartPresenter(this);
        materialAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chart, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        spMaterial = view.findViewById(R.id.sp_material);

        mReceiveChart = view.findViewById(R.id.receive_chart);
        mReceiveChart.getDescription().setEnabled(true);


        //legend.setOrientation(Legend.LegendOrientation.VERTICAL);

        mReceiveChart.setPinchZoom(true);
        mReceiveChart.setDrawGridBackground(false);
        mReceiveChart.getLegend().setEnabled(false);
        mReceiveChart.setExtraOffsets(5, 5, 5, 10);

        spMaterial.setAdapter(materialAdapter);
        mPresenter.generateNameArray(get_activity().getReceiveList(),get_activity().getConsumeList());

        spMaterial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPresenter.filter(materialAdapter.getItem(position),get_activity().getReceiveList(),get_activity().getConsumeList());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void updateMaterialAdapter(List<String> nameList) {
        materialAdapter.clear();
        materialAdapter.addAll(nameList);

        //
    }



    @Override
    public void updateChart(List<Date> dateList, List<Float> receiveList, List<Float> consumeList) {
        final List<String> xAxisLabels = new ArrayList<>();
        for (Date x: dateList){
            xAxisLabels.add(MyUtil.getStringDate(x));
        }
        IAxisValueFormatter xAxisFormatter = new DateAxisFormatter(xAxisLabels);

        XAxis xAxis = mReceiveChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelRotationAngle(90f);
        //xAxis.setTypeface(mTfLight);
        xAxis.setDrawGridLines(false);
        //xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setAxisLineColor(Color.BLACK);

        xAxis.setValueFormatter(xAxisFormatter);


        YAxis leftAxis = mReceiveChart.getAxisLeft();
        //leftAxis.setTypeface(mTfLight);
        leftAxis.setLabelCount(8, false);
        //leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f);
        //leftAxis.setAxisMaximum((float) (getVolumeofWorks()+100));

        // Disable Right Axis
        mReceiveChart.getAxisRight().setEnabled(false);

        ArrayList<Entry> receiveValues = new ArrayList<>();
        ArrayList<Entry> consumeValues = new ArrayList<>();

        for (int i=0;i<receiveList.size();i++){
            receiveValues.add(new Entry(i,receiveList.get(i)));
        }

        for (int i=0;i<consumeList.size();i++){
            consumeValues.add(new Entry(i,consumeList.get(i)));
        }

        LineDataSet receiveSet = new LineDataSet(receiveValues,"Cumulative Receive");
        receiveSet.setColor(Color.parseColor("#09af00"));
        receiveSet.setDrawCircleHole(false);
        receiveSet.setDrawCircles(false);
        receiveSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        receiveSet.setDrawValues(false);
        receiveSet.setLineWidth(3);

        LineDataSet consumeSet = new LineDataSet(consumeValues,"Cumulative Consume");
        consumeSet.setColor(Color.parseColor("#b00020"));
        consumeSet.setDrawCircleHole(false);
        consumeSet.setDrawCircles(false);
        consumeSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        consumeSet.setDrawValues(false);
        consumeSet.setLineWidth(3);

        LineData lineData = new LineData();
        lineData.addDataSet(receiveSet);
        lineData.addDataSet(consumeSet);

        lineData.setValueTextSize(10f);
        //data.setValueTypeface(mTfLight);
        mReceiveChart.setData(lineData);

        Legend legend = mReceiveChart.getLegend();
        legend.setEnabled(true);
        legend.setTextSize(14f);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        mReceiveChart.setDescription(null);
        mReceiveChart.animateXY(1000,1000);
        mReceiveChart.invalidate();
    }
}
