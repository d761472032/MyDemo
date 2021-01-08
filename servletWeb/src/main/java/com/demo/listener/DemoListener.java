package com.demo.listener;

import javax.servlet.*;

public class DemoListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println(servletContextEvent.getServletContext().getContextPath());
        System.out.println("listener-contextInitialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("listener-contextDestroyed");
    }

}
