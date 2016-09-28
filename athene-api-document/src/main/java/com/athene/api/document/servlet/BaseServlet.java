package com.athene.api.document.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by fe on 16/9/28.
 */
public class BaseServlet extends HttpServlet {

    public static final Logger logger = LoggerFactory.getLogger(BaseServlet.class);

    protected void printResult(HttpServletResponse response,String data) {
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
            printWriter.write(data);
        } catch (Exception e) {
            logger.error("printResult error , e : {}",e);
        } finally {
            if (printWriter != null) {
                printWriter.flush();
                printWriter.close();
            }
        }
    }
}
