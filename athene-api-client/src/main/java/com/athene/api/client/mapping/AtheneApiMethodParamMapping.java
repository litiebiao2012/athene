package com.athene.api.client.mapping;

/**
 * Created by fe on 16/9/18.
 *
 *  char boolean int float double long string
 *  List
 *  java.lang.Object
 *
 */
public class AtheneApiMethodParamMapping {
    private String description;
    private Object defaultValue;
    private boolean required;
    private String paramType;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }
}
