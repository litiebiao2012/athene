package com.athene.api.gateway.test;

import com.athene.api.gateway.AtheneConstants;
import com.athene.api.gateway.remote.DubboClient;
import com.athene.api.gateway.request.AtheneRequest;
import com.athene.api.gateway.test.query.UserQuery;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by fe on 16/9/9.
 */
public class GenericClient {

    public static void main(String[] args) {
        String methodName = "queryAllByParam";
        String[] parameterTypes = new String[]{"com.athene.api.gateway.test.query.UserQuery"};

        UserQuery userQuery = new UserQuery();
        userQuery.setName("aa");
        Object[] arg = new Object[]{userQuery};
        call(methodName,parameterTypes,arg);
    }

    public static void call(String methodName,String[] parameterTypes,Object[] args) {
        DubboClient dubboClient = new DubboClient();
        AtheneRequest atheneRequest = new AtheneRequest();
        atheneRequest.setServiceName("com.athene.api.gateway.test.UserService");
        atheneRequest.setMethodName(methodName);
        atheneRequest.setVersion("1.0.0");
        atheneRequest.setParameterTypes(parameterTypes);
        atheneRequest.setArgs(args);
        dubboClient.execute(atheneRequest);
    }
}
