package com.yang.rpc.starter.server;

import com.yang.rpc.registry.zookeeper.ZooKeeperServiceRegistry;
import com.yang.rpc.server.RpcServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RpcServerProerties.class)
@ConditionalOnClass(value = {ZooKeeperServiceRegistry.class, RpcServer.class})
@ConditionalOnProperty(prefix = "rpc", value = "enabled", matchIfMissing = true)
public class RpcServerAutoConfiguration {

    private final RpcServerProerties rpcServerProerties;

    @Autowired
    public RpcServerAutoConfiguration(RpcServerProerties rpcServerProerties) {
        this.rpcServerProerties = rpcServerProerties;
    }

    @Bean
    @ConditionalOnMissingBean(ZooKeeperServiceRegistry.class)
    public ZooKeeperServiceRegistry zooKeeperServiceRegistry() {
        return new ZooKeeperServiceRegistry(rpcServerProerties.getRegistryAddress());
    }

    @Bean
    @ConditionalOnMissingBean(RpcServer.class)
    public RpcServer rpcServer(ZooKeeperServiceRegistry zooKeeperServiceRegistry) {
        return new RpcServer(rpcServerProerties.getServerAddress(), zooKeeperServiceRegistry);
    }

}
