package com.demo.zookeeper;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

public class HandleWatcherAndCallback implements Watcher, AsyncCallback.DataCallback, AsyncCallback.StatCallback {

    private ZooKeeper zooKeeper;

    private String path = "";

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public void await() {
        try {
            zooKeeper.exists(path, this, this, true);
            countDownLatch.await();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Watcher
     *
     * @param watchedEvent
     */
    @Override
    public void process(WatchedEvent watchedEvent) {
        switch (watchedEvent.getType()) {
            case None:
                break;
            case NodeCreated:
                zooKeeper.getData(path, this, this, new Object());
                break;
            case NodeDeleted:
                break;
            case NodeDataChanged:
                zooKeeper.getData(path, this, this, new Object());
                break;
            case NodeChildrenChanged:
                break;
            case DataWatchRemoved:
                break;
            case ChildWatchRemoved:
                break;
            case PersistentWatchRemoved:
                break;
        }
    }

    /**
     * DataCallback，获取数据的回调方法
     *
     * @param i
     * @param s
     * @param o
     * @param bytes
     * @param stat
     */
    @Override
    public void processResult(int i, String s, Object o, byte[] bytes, Stat stat) {
        if (bytes != null) {
            System.out.println(new String(bytes));
            countDownLatch.countDown();
        }
    }

    /**
     * StatCallback
     *
     * @param i
     * @param s
     * @param o
     * @param stat
     */
    @Override
    public void processResult(int i, String s, Object o, Stat stat) {
        if (stat != null) {
            zooKeeper.getData(path, this, this, new Object());
        }
    }

}
