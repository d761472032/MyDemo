package com.demo.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DemoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println(req.getParameter("keyword"));
//        req.setAttribute("keyword", req.getParameter("keyword"));
//        req.getRequestDispatcher("result.jsp").forward(req , resp);
        resp.sendRedirect("/servletWeb/redirect");
    }

}
