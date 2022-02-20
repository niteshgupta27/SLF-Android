package com.storelogflog.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Tutorial implements Serializable
{

    @SerializedName("fld_tid")
    @Expose
    private Integer iD;
    @SerializedName("fld_tile")
    @Expose
    private String title;
    @SerializedName("fld_pdf_android")
    @Expose
    private String fld_pdf_android;

    @SerializedName("fld_video_android")
    @Expose
    private String fld_video_android;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getpdf() {
        return fld_pdf_android;
    }

    public void setpdf(String title) {
        this.fld_pdf_android = title;
    }
    public String getvideo() {
        return fld_video_android;
    }

    public void setVideo(String title) {
        this.fld_video_android = title;
    }


}