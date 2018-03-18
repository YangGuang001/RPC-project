package com.yang.rpc.registry;

/**
 * Created by yz on 2018/1/28.
 */

/**
 * 服务注册接口
 */
public interface ServiceRegistry {
    /**
     * 注册服务名称与服务地址
     * @param serviceName 服务名称
     * @param SerivceAddress 服务地址
     */
    void register(String serviceName, String SerivceAddress);
}
