
package com.storelogflog.bean.itemListBean;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.storelogflog.bean.categoryBean.Category;


public class Item implements Serializable
{

    @SerializedName("Image")
    @Expose
    private String image;

    @SerializedName("ID")
    @Expose
    private Integer iD;

    @SerializedName("StorageID")
    @Expose
    private Integer storageID;

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Desp")
    @Expose
    private String desp;
    @SerializedName("Length")
    @Expose
    private String length;
    @SerializedName("Width")
    @Expose
    private String width;
    @SerializedName("Height")
    @Expose
    private String height;
    @SerializedName("Unit")
    @Expose
    private String unit;
    @SerializedName("Qty")
    @Expose
    private Integer qty;
    @SerializedName("Value")
    @Expose
    private Integer value;
    @SerializedName("ActiveAuction")
    @Expose
    private String auction;
    @SerializedName("Location")
    @Expose
    private Integer location;

    @SerializedName("Category")
    @Expose
    private List<Category>categoryList;

    @SerializedName("Photos")
    @Expose
    private List<Photo> photos = null;


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

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getAuction() {
        return auction;
    }

    public void setAuction(String auction) {
        this.auction = auction;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public Integer getStorageID() {
        return storageID;
    }

    public void setStorageID(Integer storageID) {
        this.storageID = storageID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
