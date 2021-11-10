package com.storelogflog.bean.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginBean implements Serializable
{

    @SerializedName("IsProfileUpdated")
    @Expose
    private int isProfileUpdated;

    @SerializedName("userid")
    @Expose
    private String userid;

    @SerializedName("firstname")
    @Expose
    private String firstname;

    @SerializedName("lastname")
    @Expose
    private String lastname;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("address1")
    @Expose
    private String address1;

    @SerializedName("address2")
    @Expose
    private String address2;

    @SerializedName("city")
    @Expose
    private String city;


    @SerializedName("Image")
    @Expose
    private String Image;



    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("region")
    @Expose
    private String region;

    @SerializedName("profilephoto")
    @Expose
    private String profilephoto;

    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("devicetype")
    @Expose
    private String devicetype;
    @SerializedName("fcm")
    @Expose
    private String fcm;
    @SerializedName("devicemanufacture")
    @Expose
    private String devicemanufacture;
    @SerializedName("modelname")
    @Expose
    private String modelname;
    @SerializedName("modelnumber")
    @Expose
    private String modelnumber;
    @SerializedName("osver")
    @Expose
    private String osver;
    @SerializedName("devicename")
    @Expose
    private String devicename;

    @SerializedName("apikey")
    @Expose
    private String apikey;

    @SerializedName("secret")
    @Expose
    private String secret;



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype;
    }

    public String getFcm() {
        return fcm;
    }

    public void setFcm(String fcm) {
        this.fcm = fcm;
    }

    public String getDevicemanufacture() {
        return devicemanufacture;
    }

    public void setDevicemanufacture(String devicemanufacture) {
        this.devicemanufacture = devicemanufacture;
    }

    public String getModelname() {
        return modelname;
    }

    public void setModelname(String modelname) {
        this.modelname = modelname;
    }

    public String getModelnumber() {
        return modelnumber;
    }

    public void setModelnumber(String modelnumber) {
        this.modelnumber = modelnumber;
    }

    public String getOsver() {
        return osver;
    }

    public void setOsver(String osver) {
        this.osver = osver;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getProfilephoto() {
        return profilephoto;
    }

    public void setProfilephoto(String profilephoto) {
        this.profilephoto = profilephoto;
    }

    public int getIsProfileUpdated() {
        return isProfileUpdated;
    }

    public void setIsProfileUpdated(int isProfileUpdated) {
        this.isProfileUpdated = isProfileUpdated;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
