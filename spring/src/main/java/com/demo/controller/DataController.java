package com.demo.controller;

import com.demo.annotation.FormModel;
import com.demo.bean.Dept;
import com.demo.bean.Role;
import com.demo.bean.User;
import com.demo.denum.FormModelType;
import com.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/data")
public class DataController {

    @Autowired
    private DemoService demoService;

    @RequestMapping("/list")
    public Object list() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        return list;
    }

    @RequestMapping("/page")
    public Object page() {
        return demoService.page();
    }

    @RequestMapping("/args")
    public String getArgs(String args) {
        return args;
    }

    /**
     *
     * @param user      user.id, user.username, user.password
     * @param dept      dept.id, dept.name, dept.leader
     * @param roles     roles.id, roles.name, roles.id, roles.name, roles.id, roles.name
     * @return
     */
    @RequestMapping("/param")
    public String param(@FormModel(type = FormModelType.OBJECT, clazz = User.class, key = "user") User user,
                        @FormModel(type = FormModelType.OBJECT, clazz = Dept.class, key = "dept") Dept dept,
                        @FormModel(type = FormModelType.ARRAY, clazz = Role.class, key = "role") List<Role> roles) {
        System.out.println(user.getUsername() + user.getPassword());
        System.out.println(dept.getId());
        System.out.println(roles.size());
        return "ok";
    }

    @RequestMapping("/restful/{x}")
    public String restful(@PathVariable("x") String x) {
        return x;
    }

    @RequestMapping("/restful/{x}/{y}")
    public String restful2(@PathVariable("x") String x, @PathVariable("y") String y) {
        return x + " : " + y;
    }

    @RequestMapping("/restful/{x}/{y}/{z}")
    public String restful2(@PathVariable("x") String x, @PathVariable("y") String y, @PathVariable("z") String z) {
        return x + " : " + y + " : " + z;
    }

}
