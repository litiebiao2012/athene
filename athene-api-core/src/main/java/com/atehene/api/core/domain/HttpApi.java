package com.atehene.api.core.domain;

import java.util.List;

/**
 * Created by fe on 16/9/28.
 */
public class HttpApi {

    private String uri;
    private String description;
    private List<HttpParam> httpParamList;
    private String response;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<HttpParam> getHttpParamList() {
        return httpParamList;
    }

    public void setHttpParamList(List<HttpParam> httpParamList) {
        this.httpParamList = httpParamList;
    }
}
