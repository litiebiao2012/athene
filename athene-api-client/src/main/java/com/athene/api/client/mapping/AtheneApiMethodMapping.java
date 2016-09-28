package com.athene.api.client.mapping;

import java.util.List;

/**
 * Created by fe on 16/9/18.
 */
public class AtheneApiMethodMapping {
    private String methodName;
    private String description;
    private boolean needAuth;
    private List<AtheneApiMethodParamMapping> atheneApiMethodParamMappingList;
    private AtheneApiMethodResultMapping atheneApiMethodResultMapping;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AtheneApiMethodParamMapping> getAtheneApiMethodParamMappingList() {
        return atheneApiMethodParamMappingList;
    }

    public void setAtheneApiMethodParamMappingList(List<AtheneApiMethodParamMapping> atheneApiMethodParamMappingList) {
        this.atheneApiMethodParamMappingList = atheneApiMethodParamMappingList;
    }

    public AtheneApiMethodResultMapping getAtheneApiMethodResultMapping() {
        return atheneApiMethodResultMapping;
    }

    public void setAtheneApiMethodResultMapping(AtheneApiMethodResultMapping atheneApiMethodResultMapping) {
        this.atheneApiMethodResultMapping = atheneApiMethodResultMapping;
    }

    public boolean isNeedAuth() {
        return needAuth;
    }

    public void setNeedAuth(boolean needAuth) {
        this.needAuth = needAuth;
    }
}
