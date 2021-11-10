package com.storelogflog.bean.subscriptionBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommonMessageBean {
    @Expose
    private Integer result;
    @SerializedName("Data")
    @Expose
    private Data data;

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public class Data {

        @SerializedName("Title")
        @Expose
        private String title;
        @SerializedName("ShortDesp")
        @Expose
        private String shortDesp;
        @SerializedName("LongDesp")
        @Expose
        private String longDesp;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

    }
}

