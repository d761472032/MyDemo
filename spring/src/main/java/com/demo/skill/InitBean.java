package com.demo.skill;

import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;

/**
 * spring中支持3种初始化bean的方法：
 *  1.xml中指定init-method方法
 *  2.使用@PostConstruct注解
 *  3.实现InitializingBean接口
 *
 *  执行顺序：PostConstruct -> InitializingBean -> init-method
 */
public class InitBean implements InitializingBean {

    @PostConstruct
    public void postConstructInit() {
        System.out.println("添加@PostConstruct进行初始化");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("实现InitializingBean接口进行初始化");
    }

}
