package com.athene.api.gateway.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by fe on 16/9/21.
 */
@WebServlet(urlPatterns = "/reg.api")
public class ApiRegisterServlet extends HttpServlet {



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("reg.api");
        super.doPost(req, resp);
    }
}
