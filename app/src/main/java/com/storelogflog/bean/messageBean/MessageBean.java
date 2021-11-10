
package com.storelogflog.bean.messageBean;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageBean implements Serializable
{

    @SerializedName("result")
    @Expose
    private Integer result;
    @SerializedName("Messages")
    @Expose
    private List<Message> messages = null;

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

}
