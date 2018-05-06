package com.yang.rpc.starter.enable.client;

import com.yang.rpc.client.RpcProxy;
import com.yang.rpc.registry.zookeeper.ZookeeperServiceDiscovery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RpcClientProperties.class)
@ConditionalOnClass(value = {ZookeeperServiceDiscovery.class, RpcProxy.class})
@ConditionalOnProperty(prefix = "rpc", value = "enabled", matchIfMissing = true)
public class RpcClientAutoConfiguration {
    private final RpcClientProperties rpcClientProperties;

    @Autowired
    public RpcClientAutoConfiguration(RpcClientProperties rpcClientProperties) {
        this.rpcClientProperties = rpcClientProperties;
    }

    @Bean
    @ConditionalOnMissingBean(ZookeeperServiceDiscovery.class)
    public ZookeeperServiceDiscovery zookeeperServiceDiscovery() {
        return new ZookeeperServiceDiscovery(rpcClientProperties.getRegistryAddress());
    }

    @Bean
    @ConditionalOnMissingBean(RpcProxy.class)
    public RpcProxy rpcProxy(ZookeeperServiceDiscovery zookeeperServiceDiscovery) {
        return new RpcProxy(zookeeperServiceDiscovery);
    }
}
