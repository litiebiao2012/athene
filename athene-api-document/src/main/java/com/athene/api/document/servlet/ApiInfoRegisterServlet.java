package com.athene.api.document.servlet;

import com.alibaba.fastjson.JSONObject;
import com.atehene.api.core.domain.ApiInfo;
import com.athene.api.common.FastJson;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fe on 16/9/27.
 * {"apiInfo" : "", "apiTag" : ""}
 */
@WebServlet(urlPatterns = "/regApiInfo")
public class ApiInfoRegisterServlet extends BaseServlet {



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApiInfo apiInfo = ApiInfo.getInstance();
    }

    private Map<String,Object> processReqBody(HttpServletRequest req) {
        Map<String,Object> reqMap = new HashMap<String,Object>();
        try {
            List<String> dataList = IOUtils.readLines(req.getInputStream());
            StringBuffer jsonSb = new StringBuffer();
            if (dataList != null && dataList.size() > 0) {
                for (String data : dataList) {
                    jsonSb.append(data);
                }
            }

            JSONObject jsonObject = FastJson.fromJson(jsonSb.toString());
            List<Map<String,Object>> apiTagMap = (List)jsonObject.get("apiTag");

        } catch (IOException e) {
            logger.error("processReqBody error ,e : {}",e);

        }
        return null;
    }
}
