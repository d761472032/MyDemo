package com.demo.skill;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import javax.servlet.*;
import java.io.IOException;
import java.lang.annotation.*;

/**
 * EnableAsync、EnableCaching、EnableAspectJAutoProxy等，这类注解就像开关一样，只要在@Configuration定义的配置类上加上这类注解，就能开启相关的功能。
 */
public class EnableDemo {

    // 第四步，只需在springboot启动类加上@EnableLog注解即可开启LogFilter记录请求和响应日志的功能。

    /**
     * 第三步，定义开关@EnableLog注解
     */
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Import(LogFilterWebConfig.class)
    public @interface EnableLog {
    }

    /**
     * 第二步，注册LogFilter
     */
    @ConditionalOnWebApplication
    static public class LogFilterWebConfig {

        @Bean
        public LogFilter timeFilter() {
            return new LogFilter();
        }

    }

    /**
     * 第一步，定义一个LogFilter
     */
    static public class LogFilter implements Filter {
        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            System.out.println("记录请求日志");
            chain.doFilter(request, response);
            System.out.println("记录响应日志");
        }

        @Override
        public void destroy() {
        }
    }

}
