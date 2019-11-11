package com.forbitbd.storeapp.models;

import java.io.Serializable;

public class Supplier implements Serializable {

    private String _id;
    private String name;
    private String material_supply;
    private String contact;
    private String project;
    private String email;
    private String image;

    public Supplier() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getMaterial_supply() {
        return material_supply;
    }

    public void setMaterial_supply(String material_supply) {
        this.material_supply = material_supply;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
