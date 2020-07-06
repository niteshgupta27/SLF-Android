
package com.storelogflog.uk.bean.activeListBean;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActiveListBean implements Serializable
{

    @SerializedName("result")
    @Expose
    private Integer result;
    @SerializedName("Auctions")
    @Expose
    private List<ActiveAuction> auctions = null;


    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public List<ActiveAuction> getAuctions() {
        return auctions;
    }

    public void setAuctions(List<ActiveAuction> auctions) {
        this.auctions = auctions;
    }

}
