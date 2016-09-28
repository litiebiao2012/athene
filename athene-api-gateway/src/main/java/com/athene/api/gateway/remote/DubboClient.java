package com.athene.api.gateway.remote;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.athene.api.common.FastJson;
import com.athene.api.gateway.AtheneConstants;
import com.athene.api.gateway.request.AtheneRequest;
import com.athene.api.gateway.servlet.BaseServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by fe on 16/9/9.
 */
public class DubboClient implements RemoteClient {

    public static final Logger logger = LoggerFactory.getLogger(DubboClient.class);

    public static final String DEFAULT_VERSION = "1.0.0";

    @Override
    public Object execute(AtheneRequest atheneRequest) {
        String serviceName = atheneRequest.getServiceName();
        String methodName = atheneRequest.getMethodName();
        String version = atheneRequest.getVersion();

        if (serviceName == null || serviceName.equals("") ||
                methodName == null || methodName.equals(""))

        if (version == null || version.equals("")) version = DEFAULT_VERSION;

        ApplicationConfig application = new ApplicationConfig();
        application.setName(AtheneConstants.APPLICATION_NAME);
        application.setOwner(AtheneConstants.APPLICATION_OWNER);

        RegistryConfig registry = new RegistryConfig();
        registry.setProtocol(AtheneConstants.REGISTRY_PROTOCOL);
        registry.setAddress(AtheneConstants.REGISTRY_ADDRESS);
        registry.setTimeout(AtheneConstants.REGISTRY_TIMEOUT);

        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(serviceName);
        reference.setGeneric(true);
        reference.setCheck(false);
        reference.setVersion(version);
        ReferenceConfigCache cache = ReferenceConfigCache.getCache();

        long beforeTime = System.currentTimeMillis();
        GenericService genericService = cache.get(reference);
        long endTime = System.currentTimeMillis();
        logger.info("代理接口{},获取Reference代理对象耗时{}", new Object[] {
                serviceName,
                endTime - beforeTime });
        String[] parameterTypes = atheneRequest.getParameterTypes();
        Object[] args = atheneRequest.getArgs();
        Object obj = genericService.$invoke(methodName,parameterTypes,args);


        System.out.println(FastJson.toJson(obj));
        System.exit(-1);
        try {

        } catch (Exception e) {
        }


        return null;
    }

    private DubboRpcParam parseParam(Map<String,Object> param) {

        return null;
    }

    public static void main(String args[]) {
        Class clazz = DubboClient.class;
        Method[] methods = clazz.getMethods();

        for (Method m : methods) {
            if (m.getName().equals("execute")) {
                m.getParameterTypes();

            }
        }
    }
}

class DubboRpcParam {
    private String[] parameterTypes = null;
    private Object[] args = null;

    public String[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(String[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
