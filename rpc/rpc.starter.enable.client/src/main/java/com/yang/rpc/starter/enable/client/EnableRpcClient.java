package com.yang.rpc.starter.enable.client;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(RpcClientAutoConfiguration.class)
public @interface EnableRpcClient {
}
