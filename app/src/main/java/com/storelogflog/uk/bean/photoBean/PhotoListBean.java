
package com.storelogflog.uk.bean.photoBean;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotoListBean implements Serializable
{

    @SerializedName("result")
    @Expose
    private Integer result;
    @SerializedName("Photos")
    @Expose
    private List<Photo> photos = null;
    private final static long serialVersionUID = -7890589425626972783L;

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

}
