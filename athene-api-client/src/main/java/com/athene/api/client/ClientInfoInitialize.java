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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
                ClassPool classPool = ClassPool.getDefault();
                try {
                    CtClass ctClass = classPool.get(clazz.getName());
                    ApiInfo apiInfo = ApiInfo.getInstance();

                    ApiGroup apiGroup = (ApiGroup) ctClass.getAnnotation(ApiGroup.class);
                    if (apiGroup != null) {
                        String description = apiGroup.description();
                        String owner = apiGroup.owner();
                    }
                    CtMethod[] methods = ctClass.getMethods();
                    //TODO 重载函数需要启别名区分 未区分则athene加载抛出异常
                    checkMethods(methods,clazz.getName());

                    for (CtMethod m : methods) {
                        ApiMethod apiMethod = (ApiMethod) m.getAnnotation(ApiMethod.class);
                        if (apiMethod != null) {
                            String methodDescription = apiMethod.description();
                            String methodName = m.getName();

                            //TODO 默认取函数名,如果启动@ApiMethod属性配置,则取@ApiMethod属性配置methodName
                            if (!StringUtils.isEmpty(apiMethod.methodName())) {
                                methodName = apiMethod.methodName();
                            }
                        }

                    }

                } catch (ClassNotFoundException e) {

                } catch (NotFoundException e1) {
                }
            }
        }
    }

    /**
     * method校验
     * @param methods
     * @param className
     * @throws ClassNotFoundException
     */
    private static void checkMethods(CtMethod[] methods,String className) throws ClassNotFoundException {
        if (methods != null && methods.length > 0) {
            int methodNum = methods.length;
            Set<String> methodSet = new HashSet<String>();
            for (CtMethod m : methods) {
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
