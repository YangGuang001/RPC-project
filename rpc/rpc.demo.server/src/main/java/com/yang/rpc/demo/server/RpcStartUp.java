package com.yang.rpc.demo.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by yz on 2018/1/28.
 */
public class RpcStartUp {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcStartUp.class);

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("spring.xml");
        LOGGER.info("start up");
    }
}
