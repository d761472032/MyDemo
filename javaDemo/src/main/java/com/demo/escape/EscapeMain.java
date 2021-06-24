package com.demo.escape;

public class EscapeMain {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        for (int i = 0; i < 10000000; i++) {
            alloc();
        }

        long end = System.currentTimeMillis();
        System.out.println("花费的时间为： " + (end - start) + " ms");
        //为了方便查看堆内存中对象个数，线程sleep
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    private static void alloc() {
        //未发生逃逸
        User user = new User();
    }

    static class User {
    }

    static public class EscapeAnalysis {

        public EscapeAnalysis obj;

        /*
        1.为成员属性赋值，发生逃逸
        */
        public void setObj() {
            this.obj = new EscapeAnalysis();
        }
        //思考：如果当前的obj引用声明为static的？仍然会发生逃逸。

        /*
        2.方法返回EscapeAnalysis对象，发生逃逸
         */
        public EscapeAnalysis getInstance() {
            return obj == null ? new EscapeAnalysis() : obj;
        }

        /*
        3.引用成员变量的值，发生逃逸
         */
        public void useEscapeAnalysis1() {
            EscapeAnalysis e = getInstance();
            //getInstance().xxx()同样会发生逃逸
        }

        /*
       对象的作用域仅在当前方法中有效，没有发生逃逸
        */
        public void useEscapeAnalysis() {
            EscapeAnalysis e = new EscapeAnalysis();
        }
    }

}
