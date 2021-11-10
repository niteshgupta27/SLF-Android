
package com.storelogflog.bean.storageDetailsBean;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo implements Serializable
{

    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("URL")
    @Expose
    private String uRL;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getURL() {
        return uRL;
    }

    public void setURL(String uRL) {
        this.uRL = uRL;
    }

}
