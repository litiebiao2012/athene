package com.athene.api.gateway.request;

/**
 * Created by fe on 16/9/9.
 */
public class AtheneResponse {
    private String status;
    private String errorMsg;
    private Object data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static AtheneResponse newInstance() {
        return new AtheneResponse();
    }
}
