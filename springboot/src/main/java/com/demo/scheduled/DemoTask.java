package com.demo.scheduled;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DemoTask {

    @PostConstruct
    public void print() {
        System.out.println("DemoTask is running.");
    }

}
