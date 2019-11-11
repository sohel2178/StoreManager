package com.forbitbd.storeapp.models;

import java.util.Date;

public class Consume {

    private String _id;
    private String name;
    private String unit;
    private String project;
    private String image;
    private String issue_to;
    private String where_used;
    private double quantity;
    private Date date;
    private Date created_at;

    public Consume() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIssue_to() {
        return issue_to;
    }

    public void setIssue_to(String issue_to) {
        this.issue_to = issue_to;
    }

    public String getWhere_used() {
        return where_used;
    }

    public void setWhere_used(String where_used) {
        this.where_used = where_used;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
