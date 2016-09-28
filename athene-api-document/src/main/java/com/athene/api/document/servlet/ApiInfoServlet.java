package com.athene.api.document.servlet;

import com.alibaba.fastjson.JSONObject;
import com.atehene.api.core.domain.ApiInfo;
import com.athene.api.common.FastJson;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by fe on 16/9/28.
 */
@WebServlet(urlPatterns = "/apiInfo")
public class ApiInfoServlet extends BaseServlet {

    public static final Logger logger = LoggerFactory.getLogger(ApiInfoServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    public void doService(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApiInfo apiInfo = ApiInfo.getInstance();
        printResult(resp, FastJson.toJson(apiInfo));


    }
}
