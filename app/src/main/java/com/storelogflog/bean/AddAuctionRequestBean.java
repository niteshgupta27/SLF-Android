package com.storelogflog.bean;

import java.io.Serializable;

public class AddAuctionRequestBean implements Serializable {

    private String storageId;
    private String unitId;
    private String itemId;
    private String itemName;
    private String itemDescription;
    private String expectedValue;
    private int amount;
    private String auction_category_id;
    private String showing_amount;

    public String getShowing_amount() {
        return showing_amount;
    }

    public void setShowing_amount(String showing_amount) {
        this.showing_amount = showing_amount;
    }

    public String getAuction_category_id() {
        return auction_category_id;
    }

    public void setAuction_category_id(String auction_category_id) {
        this.auction_category_id = auction_category_id;
    }

    public int getAmount() {
        return amount;

    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

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


}
