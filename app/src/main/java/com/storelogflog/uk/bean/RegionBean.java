package com.storelogflog.uk.bean;

import java.io.Serializable;

public class RegionBean implements Serializable {

    private String name;
    private String id;


    public RegionBean(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
