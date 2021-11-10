package com.storelogflog.bean;

import java.io.Serializable;

public class AddStorageRequestBean implements Serializable {

    private String storageName;
    private String address1;
    private String address2;
    private String country;
    private String region;
    private String city;
    private String zip;
    private String storage_shaps;
    private String storage_type;
    private String storage_doors;
    private String door_type;



    private long pond;

    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public long getPond() {
        return pond;
    }

    public void setPond(long pond) {
        this.pond = pond;
    }

    public String getStorage_shaps() {
        return storage_shaps;
    }

    public void setStorage_shaps(String storage_shaps) {
        this.storage_shaps = storage_shaps;
    }

    public String getStorage_type() {
        return storage_type;
    }

    public void setStorage_type(String storage_type) {
        this.storage_type = storage_type;
    }

    public String getStorage_doors() {
        return storage_doors;
    }

    public void setStorage_doors(String storage_doors) {
        this.storage_doors = storage_doors;
    }

    public String getDoor_type() {
        return door_type;
    }

    public void setDoor_type(String door_type) {
        this.door_type = door_type;
    }
}
