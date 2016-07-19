package com.csdn.jinxu.zookeeper;


import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

/**
 * 实现描述：zookeeper实战
 *
 * @author jin.xu
 * @version v1.0.0
 * @see
 * @since 16-6-19 上午10:43
 */
public class ZookeeperInstance implements Watcher {

    private ZooKeeper zooKeeper;

    private static final int SESSION_TIME_OUT = 2000;

    /**
     * 连接zookeeper
     * @param host　zkServer地址
     * @throws IOException
     */
    public void connect(String host) throws IOException {
        zooKeeper = new ZooKeeper(host, SESSION_TIME_OUT, this);
    }

    /**
     * 监听时间，当节点发生变化的时候响应
     * @param watchedEvent　监听事件
     */
    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            System.out.println("watcher received event");
        }
    }

    /**
     * 在指定路径创建znode，并初始化数据
     * @param path　znode节点路径
     * @param data　数据
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public String createNode(String path, byte[] data) throws KeeperException, InterruptedException {
        return this.zooKeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    /**
     * 获取指定路径所有孩子节点
     * @param path　znode节点路径
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public List<String> getChildren(String path) throws KeeperException, InterruptedException {
        return this.zooKeeper.getChildren(path, false);
    }

    /**
     * 在指定路径设置数据
     * @param path　znode节点路径
     * @param data　重置数据
     * @param version　节点版本
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public Stat setData(String path, byte [] data, int version) throws KeeperException, InterruptedException {
        return this.zooKeeper.setData(path, data, version);
    }

    /**
     * 根据路径获取节点数据
     *
     * @param path　znode节点路径
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public byte[] getData(String path) throws KeeperException, InterruptedException {
        return this.zooKeeper.getData(path, false, null);
    }

    /**
     * 判断某个节点是否存在
     * @param path　znode节点路径
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public boolean exists(String path) throws KeeperException, InterruptedException {
        return null!=this.zooKeeper.exists(path,false);
    }

    /**
     * 删除节点
     *
     * @param path　znode节点路径
     * @param version　节点版本
     * @throws InterruptedException
     * @throws KeeperException
     */
    public void deleteNode(String path, int version) throws InterruptedException, KeeperException {
        this.zooKeeper.delete(path, version);
    }

    /**
     * 关闭zookeeper连接
     * @throws InterruptedException
     */
    public void closeConnect() throws InterruptedException {
        if (null != zooKeeper) {
            zooKeeper.close();
        }
    }
}

