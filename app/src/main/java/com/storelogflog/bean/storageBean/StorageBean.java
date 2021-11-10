
package com.storelogflog.bean.storageBean;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StorageBean implements Serializable
{

    @SerializedName("Storage")
    @Expose
    private List<Storage> storage = null;

    public List<Storage> getStorage() {
        return storage;
    }

    public void setStorage(List<Storage> storage) {
        this.storage = storage;
    }

}
