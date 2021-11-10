
package com.storelogflog.bean.subscriptionBean;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subscripiton implements Serializable
{

    @SerializedName("StorageID")
    @Expose
    private String storageID;

    @SerializedName("Storage")
    @Expose
    private String storage;
    @SerializedName("Desp")
    @Expose
    private String desp;
    @SerializedName("StartDate")
    @Expose
    private String startDate;
    @SerializedName("EndDate")
    @Expose
    private String endDate;
    @SerializedName("PayDate")
    @Expose
    private String payDate;
    @SerializedName("TxnID")
    @Expose
    private String txnID;

    @SerializedName("Remaing")
    @Expose
    private int remaing;

    @SerializedName("Amount")
    @Expose
    private String amount;




    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getTxnID() {
        return txnID;
    }

    public void setTxnID(String txnID) {
        this.txnID = txnID;
    }

    public int getRemaing() {
        return remaing;
    }

    public void setRemaing(int remaing) {
        this.remaing = remaing;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStorageID() {
        return storageID;
    }

    public void setStorageID(String storageID) {
        this.storageID = storageID;
    }
}
