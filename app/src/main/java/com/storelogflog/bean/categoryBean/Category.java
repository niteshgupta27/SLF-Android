
package com.storelogflog.bean.categoryBean;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category implements Serializable
{

    @SerializedName("ID")
    @Expose
    private String id;
    @SerializedName("Name")
    @Expose
    private String name;

    @SerializedName("Type")
    @Expose
    private String Type;



    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
