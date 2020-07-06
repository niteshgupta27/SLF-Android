
package com.storelogflog.uk.bean.searchStorageBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.storelogflog.uk.bean.storageBean.Storage;

import java.io.Serializable;
import java.util.List;

public class SearchStorageBean implements Serializable
{

    @SerializedName("Storage")
    @Expose
    private List<SearchStorage> storage = null;

    public List<SearchStorage> getStorage() {
        return storage;
    }

    public void setStorage(List<SearchStorage> storage) {
        this.storage = storage;
    }

}
