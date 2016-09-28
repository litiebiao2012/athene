package com.athene.api.gateway;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;

/**
 * Created by fe on 16/9/9.
 */
public class DubboConfig {
    private ApplicationConfig applicationConfig;
    private RegistryConfig registryConfig;

    private static DubboConfig dubboConfig;

    public DubboConfig() {
        applicationConfig = new ApplicationConfig();
    }

    public synchronized static DubboConfig getDubboConfig() {
        if (dubboConfig == null) dubboConfig = new DubboConfig();

        return dubboConfig;
    }


}
