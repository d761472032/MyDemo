package com.demo.zookeeper;

import org.apache.zookeeper.ZooKeeper;

public class ZkMain {

    public static void main(String[] args) {
        String ips = "192.168.1.18:2181,192.168.1.19:2181,192.168.1.20:2181,192.168.1.21:2181";
        int timeout = 3000;
        try {
            ZooKeeper zk = new ZooKeeper(ips, timeout, (e) -> {
                System.out.println(e.getPath());
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
