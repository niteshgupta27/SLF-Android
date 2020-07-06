package com.storelogflog.uk.bean;

import java.io.Serializable;

public class AddAuctionRequestBean implements Serializable {

    private String storageId;
    private String unitId;
    private String itemId;
    private String itemName;
    private String itemDescription;
    private String expectedValue;
    private String amount;

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getExpectedValue() {
        return expectedValue;
    }

    public void setExpectedValue(String expectedValue) {
        this.expectedValue = expectedValue;
    }

    public String getAmout() {
        return amount;
    }

    public void setAmout(String amount) {
        this.amount = amount;
    }
}
