
package com.storelogflog.uk.bean.categoryBean;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryBean implements Serializable
{

    @SerializedName("result")
    @Expose
    private Integer result;
    @SerializedName("Categories")
    @Expose
    private List<Category> categories = null;


    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

}
