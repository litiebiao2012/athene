package com.athene.api.client.mapping;

import java.util.List;

/**
 * Created by fe on 16/9/18.
 */
public class AtheneApiGroupMapping {

    private String serviceName;
    private String description;
    private String version;
    private String owner;
    private boolean needAuth;
    private String group;
    private List<AtheneApiMethodMapping> atheneApiMethodMappings;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isNeedAuth() {
        return needAuth;
    }

    public void setNeedAuth(boolean needAuth) {
        this.needAuth = needAuth;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<AtheneApiMethodMapping> getAtheneApiMethodMappings() {
        return atheneApiMethodMappings;
    }

    public void setAtheneApiMethodMappings(List<AtheneApiMethodMapping> atheneApiMethodMappings) {
        this.atheneApiMethodMappings = atheneApiMethodMappings;
    }
}
