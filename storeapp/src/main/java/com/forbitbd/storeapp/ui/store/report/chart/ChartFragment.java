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
import com.forbitbd.storeapp.models.LineData;
import com.forbitbd.storeapp.ui.store.report.ReportBase;
import com.forbitbd.storeapp.utils.DateAxisFormatter;
import com.forbitbd.storeapp.utils.MyUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChartFragment extends ReportBase implements ChartContract.View {

    private ChartPresenter mPresenter;

    private AppCompatSpinner spMaterial;
    private ArrayAdapter<String> materialAdapter;

    private LineChart mReceiveChart,mConsumeChart;


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
        mReceiveChart.getDescription().setEnabled(false);
        mReceiveChart.setPinchZoom(true);
        // mBarChart.setOnChartValueSelectedListener(this);
        mReceiveChart.setDrawGridBackground(false);
        mReceiveChart.getLegend().setEnabled(false);
        //mLineChart.setViewPortOffsets(60, 0, 50, 60);
        mReceiveChart.setExtraOffsets(5, 5, 5, 5);


        mConsumeChart = view.findViewById(R.id.consume_chart);
        mConsumeChart.getDescription().setEnabled(false);
        mConsumeChart.setPinchZoom(true);
        // mBarChart.setOnChartValueSelectedListener(this);
        mConsumeChart.setDrawGridBackground(false);
        mConsumeChart.getLegend().setEnabled(false);
        //mLineChart.setViewPortOffsets(60, 0, 50, 60);
        mConsumeChart.setExtraOffsets(5, 5, 5, 5);

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
        materialAdapter.addAll(nameList);

        //
    }


    @Override
    public void updateReceive(List<LineData> lineDataList) {
        updateChart(lineDataList,mReceiveChart);
    }

    public void updateChart(List<LineData> lineDataList,LineChart lineChart){
        final List<String> xAxisLabels = new ArrayList<>();
        ArrayList<Entry> yVals1 = new ArrayList<>();
        for (LineData x: lineDataList) {
            yVals1.add(new Entry(lineDataList.indexOf(x), (float) x.getValue()));
            xAxisLabels.add(MyUtil.getStringDate(x.getDate()));
        }


        IAxisValueFormatter xAxisFormatter = new DateAxisFormatter(xAxisLabels);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelRotationAngle(90f);
        //xAxis.setTypeface(mTfLight);
        xAxis.setDrawGridLines(false);
        //xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setAxisLineColor(Color.BLACK);

        xAxis.setValueFormatter(xAxisFormatter);


        YAxis leftAxis = lineChart.getAxisLeft();
        //leftAxis.setTypeface(mTfLight);
        leftAxis.setLabelCount(8, false);
        //leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f);
        //leftAxis.setAxisMaximum((float) (getVolumeofWorks()+100));

        // Disable Right Axis
        lineChart.getAxisRight().setEnabled(false);



        LineDataSet dataSet = new LineDataSet(yVals1,"Cumulative Receive");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setDrawCircleHole(false);
        dataSet.setDrawCircles(false);
        dataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        dataSet.setDrawValues(false);
        dataSet.setLineWidth(2);


        com.github.mikephil.charting.data.LineData data = new com.github.mikephil.charting.data.LineData(dataSet);
        data.setValueTextSize(10f);
        //data.setValueTypeface(mTfLight);
        lineChart.setData(data);
        lineChart.animateXY(1000,1000);
        lineChart.invalidate();

    }

    @Override
    public void updateConsume(List<LineData> lineDataList) {
        updateChart(lineDataList,mConsumeChart);
    }
}
