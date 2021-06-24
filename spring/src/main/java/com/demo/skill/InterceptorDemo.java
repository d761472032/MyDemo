package com.demo.skill;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * spring mvc拦截器根spring拦截器相比，它里面能够获取HttpServletRequest和HttpServletResponse 等web对象实例。
 * spring mvc拦截器的顶层接口是：HandlerInterceptor，包含三个方法：
 *  1.preHandle 目标方法执行前执行
 *  2.postHandle 目标方法执行后执行
 *  3.afterCompletion 请求完成时执行
 * 为了方便我们一般情况会用HandlerInterceptor接口的实现类HandlerInterceptorAdapter类。
 *
 * 假如有权限认证、日志、统计的场景，可以使用该拦截器。
 */
public class InterceptorDemo {

    // 第三步，在请求接口时spring mvc通过该拦截器，能够自动拦截该接口，并且校验权限。

    /**
     * 第二步，将该拦截器注册到spring容器
     */
    @Configuration
    static public class WebAuthConfig extends WebMvcConfigurerAdapter {

        @Bean
        public AuthInterceptor getAuthInterceptor() {
            return new AuthInterceptor();
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new AuthInterceptor());
        }
    }

    /**
     * 第一步，继承HandlerInterceptorAdapter类定义拦截器
     */
    static public class AuthInterceptor extends HandlerInterceptorAdapter {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
            String requestUrl = request.getRequestURI();
            if (checkAuth(requestUrl)) {
                return true;
            }

            return false;
        }

        private boolean checkAuth(String requestUrl) {
            System.out.println("===权限校验===");
            return true;
        }

    }

}
