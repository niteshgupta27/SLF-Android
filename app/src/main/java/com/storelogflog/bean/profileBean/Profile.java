
package com.storelogflog.bean.profileBean;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile implements Serializable
{

    @SerializedName("CountryID")
    @Expose
    private int countryID;

    @SerializedName("RegionID")
    @Expose
    private int regionID;

    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("Add1")
    @Expose
    private Object add1;
    @SerializedName("Add2")
    @Expose
    private Object add2;
    @SerializedName("Country")
    @Expose
    private String country;
    @SerializedName("Reagion")
    @Expose
    private String reagion;
    @SerializedName("City")
    @Expose
    private Object city;
    @SerializedName("Image")
    @Expose
    private String image;
    private final static long serialVersionUID = 1437605268914089351L;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public Object getAdd1() {
        return add1;
    }

    public void setAdd1(Object add1) {
        this.add1 = add1;
    }

    public Object getAdd2() {
        return add2;
    }

    public void setAdd2(Object add2) {
        this.add2 = add2;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getReagion() {
        return reagion;
    }

    public void setReagion(String reagion) {
        this.reagion = reagion;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public int getRegionID() {
        return regionID;
    }

    public void setRegionID(int regionID) {
        this.regionID = regionID;
    }
}
