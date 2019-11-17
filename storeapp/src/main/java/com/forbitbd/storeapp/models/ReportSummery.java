package com.forbitbd.storeapp.models;

public class ReportSummery {
    private String item;
    private double received;
    private double consume;


    public ReportSummery() {
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getReceived() {
        return received;
    }

    public void setReceived(double received) {
        this.received = received;
    }

    public double getConsume() {
        return consume;
    }

    public void setConsume(double consume) {
        this.consume = consume;
    }

    public void addReceived(double received){
        this.received = this.received +received;
    }

    public void addConsumed(double consume){
        this.consume = this.consume+consume;
    }

    public double getBalance(){
        return this.received-this.consume;
    }
}
