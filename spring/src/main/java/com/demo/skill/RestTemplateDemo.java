package com.demo.skill;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用RestTemplate调用远程接口时，有时需要在header中传递信息，比如：traceId，source等，便于在查询日志时能够串联一次完整的请求链路，快速定位问题。
 */
public class RestTemplateDemo {

    /**
     * 第二步，定义配置类
     */
    @Configuration
    static public class RestTemplateConfiguration {

        @Bean
        public RestTemplate restTemplate() {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setInterceptors(Collections.singletonList(restTemplateInterceptor()));
            return restTemplate;
        }

        @Bean
        public RestTemplateInterceptor restTemplateInterceptor() {
            return new RestTemplateInterceptor();
        }
    }

    /**
     *  第一步，实现ClientHttpRequestInterceptor接口
     */
    static public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            request.getHeaders().set("traceId", MdcUtil.get());
            return execution.execute(request, body);
        }
    }

    static public class MdcUtil {

        private static final String TRACE_ID = "TRACE_ID";

        private static Map<String, String> MDC = new HashMap<>();

        public static String get() {
            return MDC.get(TRACE_ID);
        }

        public static void add(String value) {
            MDC.put(TRACE_ID, value);
        }
    }

}
