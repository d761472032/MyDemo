package com.demo.service;

import com.demo.dao.mapper.DemoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoService {

    @Autowired
    private DemoMapper demoMapper;

    public PageInfo<String> page() {
        PageHelper.startPage(1, 10);
//        PageHelper.offsetPage(1, 10);
        System.out.println("12456");
        List<String> list = demoMapper.page();
        PageInfo<String> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

}
