
package com.storelogflog.bean.messageBean;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message implements Serializable
{

    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("Date")
    @Expose
    private String date;
    private final static long serialVersionUID = 8569784979813781439L;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
