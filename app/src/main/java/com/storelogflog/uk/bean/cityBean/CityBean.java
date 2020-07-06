
package com.storelogflog.uk.bean.cityBean;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityBean implements Serializable
{

    @SerializedName("result")
    @Expose
    private Integer result;
    @SerializedName("cities")
    @Expose
    private List<City> cities = null;
    private final static long serialVersionUID = 1262578862553478386L;

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

}
