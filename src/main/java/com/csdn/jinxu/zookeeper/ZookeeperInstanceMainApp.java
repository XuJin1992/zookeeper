package com.csdn.jinxu.zookeeper;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 实现描述：zookeeper测试
 *
 * @author jin.xu
 * @version v1.0.0
 * @see
 * @since 16-7-19 上午11:19
 */
public class ZookeeperInstanceMainApp {

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZookeeperInstance zookeeperInstance = new ZookeeperInstance();

        String host = "localhost:4180";

        zookeeperInstance.connect(host);
        System.out.println("1、--------connect zookeeper ok-----------\n");

        boolean isExists=zookeeperInstance.exists("/test");
        if(isExists){
            zookeeperInstance.deleteNode("/test",-1);
            System.out.println("2、--------delete znode ok-----------\n");
        }
        System.out.println("3、--------exists znode ok-----------\n");


        byte [] data = {1, 2, 3, 4, 5};
        String result = zookeeperInstance.createNode("/test", data);
        System.out.println(result);
        System.out.println("4、--------create znode ok-----------\n");


        List<String> children = zookeeperInstance.getChildren("/");
        for (String child : children)
        {
            System.out.println(child);
        }
        System.out.println("5、--------get children znode ok-----------\n");


        byte [] nodeData = zookeeperInstance.getData("/test");
        System.out.println(Arrays.toString(nodeData));
        System.out.println("6、--------get znode data ok-----------\n");


        data = "test data".getBytes();
        zookeeperInstance.setData("/test", data, 0);
        System.out.println("7、--------set znode data ok-----------\n");

        nodeData = zookeeperInstance.getData("/test");
        System.out.println(Arrays.toString(nodeData));
        System.out.println("8、--------get znode new data ok-----------\n");

        zookeeperInstance.closeConnect();
        System.out.println("9、--------close zookeeper ok-----------\n");
    }

}
