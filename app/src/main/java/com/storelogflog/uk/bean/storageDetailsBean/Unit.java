
package com.storelogflog.uk.bean.storageDetailsBean;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Unit implements Serializable
{

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Desp")
    @Expose
    private String desp;
    @SerializedName("Avail")
    @Expose
    private String avail;
    @SerializedName("Price")
    @Expose
    private String price;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public String getAvail() {
        return avail;
    }

    public void setAvail(String avail) {
        this.avail = avail;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
