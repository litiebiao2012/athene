package com.athene.api.gateway.servlet;

import com.athene.api.common.StringUtils;
import com.athene.api.gateway.request.AtheneResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by fe on 16/9/9.
 */
@WebServlet(urlPatterns = "/")
public class ServiceServlet extends BaseServlet {


    @Override
    protected void doService(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String serviceName = req.getParameter("serviceName");
        String methodName = req.getParameter("methodName");
        String version = req.getParameter("version");
        String group = req.getParameter("group");
        String data = req.getParameter("data");
        String _timeStamp = req.getParameter("");


        resp.addHeader("resp_body_des","这是一段描述!");
        AtheneResponse atheneResponse = new AtheneResponse();
        atheneResponse.setStatus("200");
        printResult(resp,atheneResponse);

    }
}
