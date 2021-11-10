
package com.storelogflog.bean.regionBean;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegionBean implements Serializable
{

    @SerializedName("result")
    @Expose
    private Integer result;
    @SerializedName("region")
    @Expose
    private List<Region> region = null;

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public List<Region> getRegion() {
        return region;
    }

    public void setRegion(List<Region> region) {
        this.region = region;
    }

}
