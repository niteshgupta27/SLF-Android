
package com.storelogflog.uk.bean.subscriptionBean;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubscriptionBean implements Serializable
{

    @SerializedName("result")
    @Expose
    private Integer result;
    @SerializedName("Subscripiton")
    @Expose
    private List<Subscripiton> subscripiton = null;
    private final static long serialVersionUID = -1453907023915313275L;

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public List<Subscripiton> getSubscripiton() {
        return subscripiton;
    }

    public void setSubscripiton(List<Subscripiton> subscripiton) {
        this.subscripiton = subscripiton;
    }

}
