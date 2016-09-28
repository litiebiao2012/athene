package com.atehene.api.core;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * Created by fe on 16/9/26.
 */
public class ApiMethodInfo {

    /**
     * 返回值类型
     */
    public Class<?> returnType;

    /**
     * 返回类型为Collection时,actuallyGenericReturnType有值,值为实际的泛型信息
     */
    public Class<?> actuallyGenericReturnType;

    /**
     * 方法名称
     */
    public String methodName;

    /**
     * 方法调用说明
     */
    public String description;

    /**
     * 方法详细说明
     */
    public String detail;

    /**
     * 方法需要的安全级别
     */
    public SecurityType securityLevel = SecurityType.None;


    /**
     * 资源所属组名
     */
    public String groupName;

    /**
     * 参数类型
     */
    public ApiParameterInfo[] parameterInfos;

    /**
     * 该方法可能抛出的业务异常的errorcode int集合, 用于二分查找
     */
    public int[] errors;

    /**
     * 所代理的方法的信息
     */
    public Method proxyMethodInfo;

    /**
     * 被代理的方法所属的接口,dubbo interface
     */
    public Class<?> dubboInterface;

    /**
     * 提供被代理方法的实例
     */
    public Object serviceInstance;

    /**
     * 资源负责人
     */
    public String owner;

    /**
     * 资源组负责人
     */
    public String groupOwner;

}
