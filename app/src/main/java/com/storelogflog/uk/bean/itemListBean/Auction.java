
package com.storelogflog.uk.bean.itemListBean;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Auction implements Serializable
{

    @SerializedName("Image")
    @Expose
    private String image;

    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Desp")
    @Expose
    private String desp;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("HighPrice")
    @Expose
    private Integer highPrice;
    @SerializedName("TotalOffers")
    @Expose
    private Integer totalOffers;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(Integer highPrice) {
        this.highPrice = highPrice;
    }

    public Integer getTotalOffers() {
        return totalOffers;
    }

    public void setTotalOffers(Integer totalOffers) {
        this.totalOffers = totalOffers;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
