package com.storelogflog.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AuctionCategoryModel {
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

        @SerializedName("AuctionCategoryID")
        @Expose
        private Integer auctionCategoryID;
        @SerializedName("AuctionCategoryName")
        @Expose
        private String auctionCategoryName;

        public Integer getAuctionCategoryID() {
            return auctionCategoryID;
        }

        public void setAuctionCategoryID(Integer auctionCategoryID) {
            this.auctionCategoryID = auctionCategoryID;
        }

        public String getAuctionCategoryName() {
            return auctionCategoryName;
        }

        public void setAuctionCategoryName(String auctionCategoryName) {
            this.auctionCategoryName = auctionCategoryName;
        }

    }
}
