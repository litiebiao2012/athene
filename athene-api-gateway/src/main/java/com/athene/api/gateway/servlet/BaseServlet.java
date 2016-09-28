package com.athene.api.gateway.servlet;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.athene.api.common.FastJson;
import com.athene.api.common.StringUtils;
import com.athene.api.gateway.AtheneConstants;
import com.athene.api.gateway.remote.DubboClient;
import com.athene.api.gateway.remote.RemoteClient;
import com.athene.api.gateway.request.AtheneRequest;
import com.athene.api.gateway.request.AtheneResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * Created by fe on 16/9/9.
 */
public abstract class BaseServlet extends HttpServlet {

    public static final Logger logger = LoggerFactory.getLogger(BaseServlet.class);


    @Override
    public void init() throws ServletException {

        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doService(req,resp);
    }

    protected abstract void doService(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;


    private AtheneRequest processRequest(HttpServletRequest req) {
        // group
        // version
        // service
        // method
        // data :[{"paramType":"java.lang.Long","paramValue":}]
        // _accessId
        // _accessSecret

        String serviceName = req.getParameter("serviceName");
        String methodName = req.getParameter("methodName");
        String version = req.getParameter("version");
        String group = req.getParameter("group");
        String data = req.getParameter("data");

        AtheneResponse atheneResponse = AtheneResponse.newInstance();
        if (StringUtils.isEmpty(serviceName)) {
            atheneResponse.setStatus(AtheneConstants.ERROR_CODE_PARAM_NOT_NULL);
            atheneResponse.setErrorMsg("serviceName不能为空!");
        }

        if (StringUtils.isEmpty(methodName)) {
            atheneResponse.setStatus(AtheneConstants.ERROR_CODE_PARAM_NOT_NULL);
            atheneResponse.setErrorMsg("methodName不能为空!");
        }

        if (StringUtils.isEmpty(version)) {
            atheneResponse.setStatus(AtheneConstants.ERROR_CODE_PARAM_NOT_NULL);
            atheneResponse.setErrorMsg("version不能为空!");
        }

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
        String[] parameterTypes = null;
        Object[] args = null;
        Object obj = genericService.$invoke(methodName,parameterTypes,args);

        return null;
    }

    protected void printResult(HttpServletResponse response,AtheneResponse atheneResponse) {
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
            printWriter.write(FastJson.toJson(atheneResponse));
            printWriter.flush();
        } catch (IOException e) {
            logger.error("printResult error !, e : {}" ,e);
        } catch (Exception e1) {
            logger.error("printResult error !, e : {}" ,e1);
        } finally {
            if (printWriter != null)
                printWriter.close();
        }

    }

}
