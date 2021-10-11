package com.demo.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExportExcel {

    public static void main(String[] args) {
        List<Demo> list = new ArrayList<>();
        for (int i = 0; i < 1000000; i ++) {
            list.add(new Demo("no" + i, "w" + i));
        }
        EasyExcel.write("test.xlsx", Demo.class).sheet().doWrite(list);
    }

    static class Demo implements Serializable {

        @ExcelProperty("编号")
        private String id;

        @ExcelProperty("姓名")
        private String name;

        public Demo(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
