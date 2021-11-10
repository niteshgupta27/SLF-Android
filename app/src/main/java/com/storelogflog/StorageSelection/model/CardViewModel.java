package com.storelogflog.StorageSelection.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CardViewModel implements Serializable{

    @SerializedName("result")
    @Expose
    private Integer result;
    @SerializedName("Items")
    @Expose
    private List<Item> items = null;
    @SerializedName("Auction")
    @Expose
    private List<Auction> auction = null;



    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Auction> getAuction() {
        return auction;
    }

    public void setAuction(List<Auction> auction) {
        this.auction = auction;
    }


    public class Auction implements Serializable{

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
        private String highPrice;
        @SerializedName("TotalOffers")
        @Expose
        private Integer totalOffers;
        @SerializedName("Image")
        @Expose
        private String image;
        @SerializedName("StorageName")
        @Expose
        private String storageName;

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

        public String getHighPrice() {
            return highPrice;
        }

        public void setHighPrice(String highPrice) {
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

        public String getStorageName() {
            return storageName;
        }

        public void setStorageName(String storageName) {
            this.storageName = storageName;
        }

    }

    public static class Item implements Serializable {

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
        @SerializedName("Currency")
        @Expose
        private String Currency;
        @SerializedName("Qty")
        @Expose
        private String qty;
        @SerializedName("Value")
        @Expose
        private Integer value;
        @SerializedName("Auction")
        @Expose
        private String auction;
        @SerializedName("LocationId")
        @Expose
        private Integer locationId;
        @SerializedName("LocationValue")
        @Expose
        private Integer locationValue;
        @SerializedName("RackId")
        @Expose
        private Integer rackId;
        @SerializedName("RackValue")
        @Expose
        private List<RackValue> rackValue = null;
        @SerializedName("Photos")
        @Expose
        private List<Photo> photos = null;
        @SerializedName("Category")
        @Expose
        private List<Category> category = null;
        @SerializedName("Image")
        @Expose
        private List<Image> image = null;

        String Shape_name;
        public String getShape_name() {
            return Shape_name;
        }

        public void setShape_name(String shape_name) {
            Shape_name = shape_name;
        }




        private String shape_id2;

        public String getShape_id2() {
            return shape_id2;
        }

        public void setShape_id2(String shape_id2) {
            this.shape_id2 = shape_id2;
        }

        public Integer getID() {
            return iD;
        }

        public void setID(Integer iD) {
            this.iD = iD;
        }

        public Integer getStorageID() {
            return storageID;
        }

        public void setStorageID(Integer storageID) {
            this.storageID = storageID;
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

        public String getCurrency() {
            return Currency;
        }

        public void setCurrency(String currency) {
            Currency = currency;
        }


        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
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

        public Integer getLocationId() {
            return locationId;
        }

        public void setLocationId(Integer locationId) {
            this.locationId = locationId;
        }

        public Integer getLocationValue() {
            return locationValue;
        }

        public void setLocationValue(Integer locationValue) {
            this.locationValue = locationValue;
        }

        public Integer getRackId() {
            return rackId;
        }

        public void setRackId(Integer rackId) {
            this.rackId = rackId;
        }

        public List<RackValue> getRackValue() {
            return rackValue;
        }

        public void setRackValue(List<RackValue> rackValue) {
            this.rackValue = rackValue;
        }

        public List<Photo> getPhotos() {
            return photos;
        }

        public void setPhotos(List<Photo> photos) {
            this.photos = photos;
        }

        public List<Category> getCategory() {
            return category;
        }

        public void setCategory(List<Category> category) {
            this.category = category;
        }

        public List<Image> getImage() {
            return image;
        }

        public void setImage(List<Image> image) {
            this.image = image;
        }


        public class Photo implements Serializable{

            @SerializedName("ID")
            @Expose
            private Integer iD;
            @SerializedName("URL")
            @Expose
            private String uRL;

            public Integer getID() {
                return iD;
            }

            public void setID(Integer iD) {
                this.iD = iD;
            }

            public String getURL() {
                return uRL;
            }

            public void setURL(String uRL) {
                this.uRL = uRL;
            }

        }

        public class RackValue implements Serializable{

            @SerializedName("RackID")
            @Expose
            private Integer rackID;

            public Integer getRackID() {
                return rackID;
            }

            public void setRackID(Integer rackID) {
                this.rackID = rackID;
            }

        }

        public class Category implements Serializable{

            @SerializedName("ID")
            @Expose
            private Integer iD;
            @SerializedName("Name")
            @Expose
            private String name;

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

        }


        public class Image implements Serializable{

            @SerializedName("ID")
            @Expose
            private Integer iD;
            @SerializedName("URL")
            @Expose
            private String uRL;

            public Integer getID() {
                return iD;
            }

            public void setID(Integer iD) {
                this.iD = iD;
            }

            public String getURL() {
                return uRL;
            }

            public void setURL(String uRL) {
                this.uRL = uRL;
            }

        }

    }
}
