package com.athene.api.gateway;

import java.util.ResourceBundle;

/**
 * Created by fe on 16/9/9.
 */
public class AtheneConstants {

    public static final ResourceBundle bundle = ResourceBundle.getBundle("athene");

    public static final String APPLICATION_NAME = bundle.getString("athene.api.gateway.dubbo.application.name");
    public static final String APPLICATION_OWNER = bundle.getString("athene.api.gateway.dubbo.application.owner");
    public static final String REGISTRY_PROTOCOL = bundle.getString("athene.api.gateway.dubbo.registry.protocol");
    public static final String REGISTRY_ADDRESS = bundle.getString("athene.api.gateway.dubbo.registry.address");
    public static final String REGISTRY_CLIENT = bundle.getString("athene.api.gateway.dubbo.registry.client");
    public static final Integer REGISTRY_TIMEOUT = Integer.parseInt(bundle.getString("athene.api.gateway.dubbo.registry.timeout"));



    public static final String DUBBO_VERSION_KEY = "_v";


    public static final String RESULT_CODE_SUCCESS = "200";


    public static final String ERROR_CODE_PARAM_NOT_NULL = "500";





}
