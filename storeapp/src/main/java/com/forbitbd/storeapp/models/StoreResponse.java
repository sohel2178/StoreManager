package com.forbitbd.storeapp.models;

import java.util.List;

public class StoreResponse {

    private List<Supplier> suppliers;
    private List<Receive> receives;
    private List<Consume> consumes;


    public StoreResponse() {
    }


    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    public List<Receive> getReceives() {
        return receives;
    }

    public void setReceives(List<Receive> receives) {
        this.receives = receives;
    }

    public List<Consume> getConsumes() {
        return consumes;
    }

    public void setConsumes(List<Consume> consumes) {
        this.consumes = consumes;
    }
}
