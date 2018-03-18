package com.yang.rpc.demo.server;

import com.yang.rpc.demo.api.HelloService;
import com.yang.rpc.server.RpcService;

/**
 * Created by yz on 2018/1/28.
 */
@RpcService(value = HelloService.class, version = "v2")
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String str) {
        return str + "world";
    }
}
