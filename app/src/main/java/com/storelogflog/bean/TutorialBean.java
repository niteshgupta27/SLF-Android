package com.storelogflog.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.io.Serializable;
import java.util.List;

public class TutorialBean implements Serializable
{
    @SerializedName("result")
    @Expose
    private Integer result;
    @SerializedName("data")
    @Expose
    private List<Tutorial> Tutoriallist = null;


    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public  List<Tutorial> gettutorial() {
        return Tutoriallist;
    }

    public void settutorial(List<Tutorial> notifications) {
        this.Tutoriallist = notifications;
    }

}