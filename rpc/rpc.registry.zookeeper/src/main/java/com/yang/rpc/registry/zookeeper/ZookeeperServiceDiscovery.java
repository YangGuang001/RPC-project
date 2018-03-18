package com.yang.rpc.registry.zookeeper;

import com.yang.rpc.registry.ServiceDiscovery;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by yz on 2018/1/28.
 */
public class ZookeeperServiceDiscovery implements ServiceDiscovery{
    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperServiceDiscovery.class);

    private String zkAddress;

    public ZookeeperServiceDiscovery(String zkAddress) {
        this.zkAddress = zkAddress;
    }

    @Override
    public String discover(String serviceName) {
        //创建zkclient
        ZkClient zkClient = new ZkClient(zkAddress, Constant.ZK_SESSION_TIMEOUT, Constant.ZK_CONNECTION_TIMEOUT);
        LOGGER.debug("connect zookeeper");
        try {
            String servicePath = Constant.ZK_REGISTRY_PATH + "/" + serviceName;
            if (!zkClient.exists(servicePath)) {
                throw new RuntimeException(String.format("can not find any service node on path: %s", servicePath));
            }

            List<String> addressList = zkClient.getChildren(servicePath);
            if (CollectionUtils.isEmpty(addressList)) {
                throw new RuntimeException(String.format("can not find any address node on path: %s", servicePath));
            }

            //获取addrss 节点
            String address;
            int size = addressList.size();
            if (size == 1) {
                address = addressList.get(0);
                LOGGER.debug("get only address node: {}", address);
            } else {
                address = addressList.get(ThreadLocalRandom.current().nextInt(size));
                LOGGER.debug("get random address node: {}", address);
            }
            // 获取 address 节点的值
            String addressPath = servicePath + "/" + address;
            return zkClient.readData(addressPath);
        }finally {
            zkClient.close();
        }
    }
}
