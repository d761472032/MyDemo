package com.demo.skill;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * 在使用异步功能时，通常情况下有三种方式：
 *  1.继承Thread类
 *  2.实现Runable接口
 *  3.使用线程池
 */
public class AsyncDemo {

    /**
     * 继承Thread类
     */
    static public class MyThread extends Thread {

        @Override
        public void run() {
            System.out.println("===call MyThread===");
        }

        public static void main(String[] args) {
            new MyThread().start();
        }
    }

    /**
     * 实现Runable接口
     */
    static public class MyWork implements Runnable {
        @Override
        public void run() {
            System.out.println("===call MyWork===");
        }

        public static void main(String[] args) {
            new Thread(new MyWork()).start();
        }
    }

    /**
     * 使用线程池
     */
    static public class MyThreadPool {

        private static ExecutorService executorService = new ThreadPoolExecutor(1, 5, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(200));

        static class Work implements Runnable {

            @Override
            public void run() {
                System.out.println("===call work===");
            }
        }

        public static void main(String[] args) {
            try {
                executorService.submit(new MyThreadPool.Work());
            } finally {
                executorService.shutdown();
            }

        }
    }

    /**
     *  默认情况下，spring会为我们的异步方法创建一个线程去执行，如果该方法被调用次数非常多的话，需要创建大量的线程，会导致资源浪费。
     *  这时，我们可以定义一个线程池，异步方法将会被自动提交到线程池中执行。
     */
    @Configuration
    static public class ThreadPoolConfig {

        @Value("${thread.pool.corePoolSize:5}")
        private int corePoolSize;

        @Value("${thread.pool.maxPoolSize:10}")
        private int maxPoolSize;

        @Value("${thread.pool.queueCapacity:200}")
        private int queueCapacity;

        @Value("${thread.pool.keepAliveSeconds:30}")
        private int keepAliveSeconds;

        @Value("${thread.pool.threadNamePrefix:ASYNC_}")
        private String threadNamePrefix;

        @Bean
        public Executor MessageExecutor() {
            ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
            executor.setCorePoolSize(corePoolSize);
            executor.setMaxPoolSize(maxPoolSize);
            executor.setQueueCapacity(queueCapacity);
            executor.setKeepAliveSeconds(keepAliveSeconds);
            executor.setThreadNamePrefix(threadNamePrefix);
            executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
            executor.initialize();
            return executor;
        }
    }

    /**
     * 第二步，在需要使用异步的方法上加上@Async注解
     */
    @Service
    static public class PersonService {

        @Async
        public String get() {
            System.out.println("===add==");
            return "data";
        }
    }

    /**
     * 第一步，springboot项目启动类上加@EnableAsync注解
     */
    @EnableAsync
    @SpringBootApplication
    static public class Application {

        public static void main(String[] args) {
            new SpringApplicationBuilder(Application.class).web(WebApplicationType.SERVLET).run(args);
        }

    }

}
