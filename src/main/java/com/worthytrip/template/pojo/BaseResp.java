package com.worthytrip.template.pojo;

/**
 * Created by xubin on 2018/3/7.
 */
public class BaseResp {
    private Integer status;
    private String message;
    private Object data;

    public BaseResp() {

    }

    public BaseResp(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
