package com.athene.api.gateway.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by fe on 16/9/20.
 */
@WebServlet(urlPatterns = "/tp.api")
public class ThirtyPartServlet extends BaseServlet {

    @Override
    protected void doService(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("tp.api");
    }
}
