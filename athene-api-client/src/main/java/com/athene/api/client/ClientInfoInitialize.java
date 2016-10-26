package com.athene.api.client;

import com.atehene.api.core.domain.ApiInfo;
import com.athene.api.client.annotation.ApiGroup;
import com.athene.api.client.annotation.ApiMethod;
import com.athene.api.client.exception.AtheneClientException;
import com.athene.api.client.mapping.AtheneApiGroupMapping;
import com.athene.api.common.StringUtils;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.LocalVariableTypeAttribute;
import javassist.bytecode.MethodInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by fe on 16/9/18.
 */
public class ClientInfoInitialize {
    public static Logger logger = LoggerFactory.getLogger(ClientInfoInitialize.class);

    /**
     * 搜索接口包地址 接口根路径,不同根路径地址 | 分割    com.sh.service | com.hz.service
     */
    public static final String ATHENE_CLIENT_PATH_KEY = "athene.client.path";

    /**
     * 网关服务地址  api01.com|api02.com 多个网关地址 | 分割
     */
    public static final String ATHENE_API_GATEWAY_HOST = "athene.api.gateway.host";

    /**
     * 网关document地址
     */
    public static final String ATHENE_API_DOCUMENT_HOST = "athene.api.document.host";

    public static final String apiGateWayUri = "/reg.api";

    public static final String apiDocumentUri = "/regApiInfo";

    public static final List<String> apiGateWayUrls = new LinkedList<String>();

    public static final List<String> excludeMethodNameList = new ArrayList<String>() {
        {
            add("clone");
            add("equals");
            add("finalize");
            add("getClass");
            add("hashCode");
            add("notify");
            add("notifyAll");
            add("registerNatives");
            add("toString");
            add("wait");
        }
    };

    static {
        init();
    }

    public static void init() {
        String atheneClientPathKey = System.getProperty(ATHENE_CLIENT_PATH_KEY);

        if (StringUtils.isEmpty(atheneClientPathKey))
            throw new AtheneClientException(ATHENE_CLIENT_PATH_KEY + ",not config, please check jvm env!");

        String atheneApiGateWayHost = System.getProperty(ATHENE_API_GATEWAY_HOST);

        if (StringUtils.isEmpty(atheneApiGateWayHost))
            throw new AtheneClientException(ATHENE_API_GATEWAY_HOST + ",not config, please check jvm env!");

        String atheneApiDocumentHost = System.getProperty(ATHENE_API_DOCUMENT_HOST);

        if (StringUtils.isEmpty(atheneApiDocumentHost))
            throw new AtheneClientException(ATHENE_API_DOCUMENT_HOST + ",not config, please check jvm env!");


        String[] hosts = atheneApiGateWayHost.split("\\|");
        for (String host : hosts) {
            apiGateWayUrls.add(host + apiGateWayUri);
        }
        String apiDocumentUrl = atheneApiDocumentHost + apiDocumentUri;

        reg(atheneClientPathKey,apiGateWayUrls,apiDocumentUrl);
    }

    public static void reg(String atheneClientPathKey,List<String> apiGateWayUrls,String apiDocumentUrl) {
        String classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

        String[] packagePath = atheneClientPathKey.split("\\|");

        File[] files = new File[packagePath.length];
        int index = 0;
        for (String path : packagePath) {
            files[index] = new File(classPath + path.replace(".","/"));
            index++;
        }

        List<Class<?>> clientInterfaceClassList = new LinkedList<Class<?>>();
        loadClazzInterface(files,clientInterfaceClassList);

        processClazzList(clientInterfaceClassList);

        for (String host : apiGateWayUrls) {
            logger.info("begin register api to gateway , host : {}",host);
            registerClientApiToGateWay(host);
            logger.info("host : {} register success!",host);
        }

        logger.info("begin send data to api document, host : {}",apiDocumentUrl);
        sendDataToApiDocument(apiDocumentUrl);
        logger.info("send data to api document success!");


    }


    /**
     * 搜索工程classPath下的接口class
     * @param files
     * @param clientInterfaceClassList
     */
    private static void loadClazzInterface(File[] files, List<Class<?>> clientInterfaceClassList) {
        for (File file : files) {
            if (file.isFile()) {
                loadClazz(file,clientInterfaceClassList);
            } else {
                File[] childFiles = file.listFiles();
                if (childFiles != null && childFiles.length > 0) {
                    for (File childFile : childFiles) {
                        if (childFile.isDirectory()) {
                            loadClazzInterface(childFile.listFiles(),clientInterfaceClassList);
                        } else {
                            loadClazz(childFile,clientInterfaceClassList);
                        }
                    }
                }
            }

        }
    }

    private static void loadClazz(File file,List<Class<?>> clientInterfaceClassList) {
        int start = file.getPath().indexOf("com");
        int end = file.getPath().lastIndexOf(".");
        String className = file.getPath().substring(start,end).replace("/",".");
        try {

            Class<?> clazz = Class.forName(className);
            if (clazz.isInterface()) {
                ApiGroup apiGroup = clazz.getAnnotation(ApiGroup.class);
                if (apiGroup != null) {
                    logger.info("athene client load clazz : {}",className);
                    clientInterfaceClassList.add(clazz);
                }
            }
        } catch (Exception e) {
            logger.error("load clazz error, e : {}",e);
        }
    }

    /**
     * 解析接口
     * @param clientInterfaceClassList
     */
    private static void processClazzList(List<Class<?>> clientInterfaceClassList) {
        if (clientInterfaceClassList != null && clientInterfaceClassList.size() > 0) {
            for (Class clazz : clientInterfaceClassList) {
                try {
                    Method[] methods = clazz.getDeclaredMethods();
                    //TODO 重载函数需要启别名区分 未区分则athene加载抛出异常
                    checkMethods(methods,clazz.getName());
                    for (Method m : methods) {
                        ApiMethod apiMethod = (ApiMethod) m.getAnnotation(ApiMethod.class);
                        String methodDescription = "";
                        String methodName = m.getName();
                        if (apiMethod != null) {
                            methodDescription = apiMethod.description();
                            //TODO 默认取函数名,如果启动@ApiMethod属性配置,则取@ApiMethod属性配置methodName
                            if (!StringUtils.isEmpty(apiMethod.methodName())) {
                                methodName = apiMethod.methodName();
                            }
                        }

                        //TODO 解析method 参数
                        parseMethodParameter(m);


                        //TODO 解析method返回结果
                    }

                }  catch (NotFoundException e1) {

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 解析method 请求参数
     * @param m
     */
    private static void parseMethodParameter(Method m) throws NotFoundException {

        List<String> nameList = MethodParamNamesScaner.getParamNames(m);


        Class<?> clazz = m.getDeclaringClass();
        ClassPool pool = ClassPool.getDefault();
        CtClass clz = pool.get(clazz.getName());


        CtClass[] params = new CtClass[m.getParameterTypes().length];
        for (int i = 0; i < m.getParameterTypes().length; i++) {
            params[i] = pool.getCtClass(m.getParameterTypes()[i].getName());
        }
        CtMethod cm = clz.getDeclaredMethod(m.getName(), params);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();

    }

    /**
     * 解析method返回结果
     * @param ctMethod
     */
    private static void parseMethodReturnType(CtMethod ctMethod) {

    }


    /**
     * method校验
     * @param methods
     * @param className
     * @throws ClassNotFoundException
     */
    private static void checkMethods(Method[] methods,String className) throws ClassNotFoundException {
        if (methods != null && methods.length > 0) {
            int methodNum = methods.length ;
            Set<String> methodSet = new HashSet<String>();
            for (Method m : methods) {
                ApiMethod apiMethod = (ApiMethod) m.getAnnotation(ApiMethod.class);
                String methodName = m.getName();
                if (apiMethod != null) {
                    if (!StringUtils.isEmpty(apiMethod.methodName())) methodName = apiMethod.methodName();
                }
                methodSet.add(methodName);
            }

            if (methodNum != methodSet.size()) throw new AtheneClientException("find [" + className + "] has same method!  please check!  you can use @ApiMethod.methodName() fix this problem!");
        }
    }


    /**
     * 接口信息注册至apiGateWay
     */
    public static void registerClientApiToGateWay(String clientApiGateWayHost) {

    }

    /**
     * 接口描述信息发送至document
     */
    public static void sendDataToApiDocument(String apiDocumentHost) {

    }


}
