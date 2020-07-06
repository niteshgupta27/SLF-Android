
package com.storelogflog.uk.bean.itemListBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Photo implements Serializable
{

    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("URL")
    @Expose
    private String uRL;


    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getURL() {
        return uRL;
    }

    public void setURL(String uRL) {
        this.uRL = uRL;
    }

}
