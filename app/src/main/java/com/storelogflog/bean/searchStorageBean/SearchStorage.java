
package com.storelogflog.bean.searchStorageBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SearchStorage implements Serializable
{

    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("ShortDesp")
    @Expose
    private String shortDesp;
    @SerializedName("LongDesp")
    @Expose
    private String longDesp;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("Availability")
    @Expose
    private String availability;
    @SerializedName("Pricing")
    @Expose
    private String pricing;
    @SerializedName("Photos")
    @Expose
    private List<Object> photos = null;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("Address1")
    @Expose
    private String address1;
    @SerializedName("Address2")
    @Expose
    private String address2;
    @SerializedName("Country")
    @Expose
    private String country;
    @SerializedName("Region")
    @Expose
    private String region;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("Zip")
    @Expose
    private String zip;
    @SerializedName("Units")
    @Expose
    private List<Object> units;

    @SerializedName("UnitID")
    @Expose
    private String UnitID;

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

    public String getShortDesp() {
        return shortDesp;
    }

    public void setShortDesp(String shortDesp) {
        this.shortDesp = shortDesp;
    }

    public String getLongDesp() {
        return longDesp;
    }

    public void setLongDesp(String longDesp) {
        this.longDesp = longDesp;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getPricing() {
        return pricing;
    }

    public void setPricing(String pricing) {
        this.pricing = pricing;
    }

    public List<Object> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Object> photos) {
        this.photos = photos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public List<Object> getUnits() {
        return units;
    }

    public void setUnits( List<Object> units) {
        this.units = units;
    }

    public String getUnitID() {
        return UnitID;
    }

    public void setUnitID(String unitID) {
        UnitID = unitID;
    }
}
