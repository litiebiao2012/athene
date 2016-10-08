package com.atehene.api.core.domain;

import java.util.*;

/**
 * Created by fe on 16/9/28.
 *
 *
 * {
 "swagger": "2.0",
 "info": {
 "description": "雅典娜统一接口平台",
 "version": "1.0.0",
 "title": "Athene",
 "contact": {
 "email": "litiebiao2012@163.com"
 }
 },
 "host": "localhost:3030",
 "basePath": "/api",
 "tags": [
 {
 "name": "user",
 "description": "用户中心服务"
 }
 ],
 "schemes": [
 "http"
 ],
 "paths": {
 "/user/findAllUsers": {
 "post": {
 "tags": [
 "user"
 ],
 "summary": "查找所有用户",
 "description": "查找所有用户",
 "operationId": "findAllUsers",
 "produces": [
 "application/json"
 ],
 "parameters": [
 {
 "name": "page",
 "in": "formData",
 "description": "页码",
 "required": true,
 "type": "boolean"
 },
 {
 "name": "pageSize",
 "in": "formData",
 "description": "每页大小",
 "required": true,
 "defaultValue" : "aaa",
 "type": "integer"
 },
 {
 "name": "userName",
 "in": "formData",
 "description": "用户名",
 "required": true,
 "type": "string"
 }
 ]
 }
 }
 },
 "externalDocs": {
 "description": "Athene统一接口管理平台,后端dubbo rpc需要依赖athene-client.jar,athene-client.jar会在dubbo rpc服务启动同时将rpc服务发布至athene-api-gateway网关上,同时将接口描述信息发布至该平台上",
 "url": "https://github.com/litiebiao2012/athene"
 }
 }
 */
public class ApiInfo {

    public static final String ATHENE_API_GATEWAY_HOST = System.getProperty("athene.api.gateway.host");

    public static ApiInfo apiInfo;

    private String swagger = "2.0";
    private Map<String,Object> info;
    private String host = ATHENE_API_GATEWAY_HOST;
    private String basePath = "/";
    private List<Map<String,Object>> tags = new LinkedList<Map<String,Object>>();
    private List<String> schemes;
    private Map<String,Map<String,ApiDetail>> paths = new LinkedHashMap<String,Map<String,ApiDetail>>();
    private Map<String,Object> externalDocs;

    private static Object mutex = new Object();

    private ApiInfo() {
        info = new LinkedHashMap<String,Object>();
        info.put("description","雅典娜统一接口平台");
        info.put("version","1.0.0");
        info.put("title","Athene");
        Map<String,Object> contactMap = new LinkedHashMap<String,Object>();
        contactMap.put("email","litiebiao2012@163.com");
        info.put("contact",contactMap);

        schemes = new LinkedList<String>();
        schemes.add("http");

        externalDocs = new LinkedHashMap<String,Object>();
        Map<String,Object> externalDocsMap = new HashMap<>();
        externalDocsMap.put("description","Athene统一接口管理平台,后端dubbo rpc需要依赖athene-client.jar,athene-client.jar会在dubbo rpc服务启动同时将rpc服务发布至athene-api-gateway网关上,同时将接口描述信息发布至该平台上");
        externalDocsMap.put("url","https://github.com/litiebiao2012/athene");
    }


    public String getSwagger() {
        return swagger;
    }

    public void setSwagger(String swagger) {
        this.swagger = swagger;
    }

    public Map<String, Object> getInfo() {
        return info;
    }

    public void setInfo(Map<String, Object> info) {
        this.info = info;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public List<Map<String, Object>> getTags() {
        return tags;
    }

    public void setTags(List<Map<String, Object>> tags) {
        this.tags = tags;
    }

    public List<String> getSchemes() {
        return schemes;
    }

    public void setSchemes(List<String> schemes) {
        this.schemes = schemes;
    }


    public Map<String, Object> getExternalDocs() {
        return externalDocs;
    }

    public static ApiInfo getApiInfo() {
        return apiInfo;
    }

    public static void setApiInfo(ApiInfo apiInfo) {
        ApiInfo.apiInfo = apiInfo;
    }

    public Map<String, Map<String, ApiDetail>> getPaths() {
        return paths;
    }

    public void setPaths(Map<String, Map<String, ApiDetail>> paths) {
        this.paths = paths;
    }

    public void setExternalDocs(Map<String, Object> externalDocs) {
        this.externalDocs = externalDocs;
    }

    public void addTags(String name,String description) {
        Map<String,Object> tagMap = new HashMap<>();
        tagMap.put("name",name);
        tagMap.put("description",description);
        tags.add(tagMap);
    }

    public void addApiDetail(String url,ApiDetail apiDetail) {
        Map<String,ApiDetail> apiDetailMap = new HashMap<String,ApiDetail>();
        apiDetailMap.put("post",apiDetail);
        paths.put(url,apiDetailMap);
    }

    public static ApiInfo getInstance() {
        synchronized (mutex) {
            if (apiInfo == null) apiInfo = new ApiInfo();
            return apiInfo;
        }
    }
}
