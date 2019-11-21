package com.forbitbd.storeapp.utils;

import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.List;

/**
 * Created by sohel on 20-04-18.
 */

public class DateAxisFormatter implements IAxisValueFormatter {

    private List<String> dateList;

    public DateAxisFormatter(List<String> dateList) {
        this.dateList = dateList;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int index = (int) value;

        if(index>=dateList.size()){
            index = dateList.size()-1;
        }
        return dateList.get(index);
    }
}
