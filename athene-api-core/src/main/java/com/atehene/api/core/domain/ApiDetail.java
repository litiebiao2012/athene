package com.atehene.api.core.domain;

import java.util.List;

/**
 * Created by fe on 16/9/28.
 */
public class ApiDetail {

    private List<String> tags;
    private String summary;
    private String description;
    private String operationId;
    private List<String> produces;
    private List<ApiParam> parameters;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public List<String> getProduces() {
        return produces;
    }

    public void setProduces(List<String> produces) {
        this.produces = produces;
    }

    public List<ApiParam> getParameters() {
        return parameters;
    }

    public void setParameters(List<ApiParam> parameters) {
        this.parameters = parameters;
    }
}
