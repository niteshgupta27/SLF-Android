package com.storelogflog.bean.ArchiveListBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArchiveListBean {
    @SerializedName("result")
    @Expose
    private Integer result;
    @SerializedName("Auctions")
    @Expose
    private List<Auction> auctions = null;

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public List<Auction> getAuctions() {
        return auctions;
    }

    public void setAuctions(List<Auction> auctions) {
        this.auctions = auctions;
    }

    public class Auction {

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

        public String getStorageName() {
            return storageName;
        }

        public void setStorageName(String storageName) {
            this.storageName = storageName;
        }

    }
}
