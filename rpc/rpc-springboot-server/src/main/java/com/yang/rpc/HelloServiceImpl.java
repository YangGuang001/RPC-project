package com.yang.rpc;

import com.yang.rpc.demo.api.HelloService;
import com.yang.rpc.server.RpcService;


@RpcService(value = HelloService.class, version = "v2")
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String str) {
        return str + "world";
    }
}
