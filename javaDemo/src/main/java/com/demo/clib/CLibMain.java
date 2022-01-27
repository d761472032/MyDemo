package com.demo.clib;

public class CLibMain {

    public static void main(String[] args) {
        System.loadLibrary("HelloWorld");
        CLibCall cLibCall = new CLibCall();
        cLibCall.print();
    }

}
