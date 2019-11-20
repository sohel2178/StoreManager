package com.forbitbd.storeapp.models;

import java.util.Date;

public class LineData {
    private Date date;
    private float value;

    public LineData() {
    }

    public LineData(Date date){
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void addValue(float value){
        this.value = this.value+value;
    }
}
