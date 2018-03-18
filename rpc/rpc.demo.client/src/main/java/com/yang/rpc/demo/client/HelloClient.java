package com.yang.rpc.demo.client;

import com.yang.rpc.client.RpcProxy;
import com.yang.rpc.demo.api.HelloService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by yz on 2018/1/28.
 */
public class HelloClient {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        RpcProxy rpcProxy = context.getBean(RpcProxy.class);

        HelloService helloService = rpcProxy.create(HelloService.class, "v2");
        String result = helloService.hello("Hello ");

        System.out.println(result);
    }
}
