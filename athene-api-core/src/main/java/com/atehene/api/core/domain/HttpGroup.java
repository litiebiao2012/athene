package com.atehene.api.core.domain;

import java.util.List;

/**
 * Created by fe on 16/9/28.
 */
public class HttpGroup {
    private String groupName;
    private String description;
    private String owner;
    private List<HttpApi> httpApiList;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
