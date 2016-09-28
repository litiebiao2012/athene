package com.athene.api.client.mapping;

import com.athene.api.client.annotation.ApiField;
import com.athene.api.client.annotation.ApiParam;

/**
 * Created by fe on 16/9/18.
 */
public class AtheneApiMethodResultMapping {
    private boolean isSuccess;
    private String code;
    private String msg;
    private Object data;
    private Class<?> resultType;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public Class<?> getResultType() {
        return resultType;
    }

    public void setResultType(Class<?> resultType) {
        this.resultType = resultType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
