package com.yang.rpc;

import com.yang.rpc.starter.enable.server.EnableRpcServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRpcServer
public class RpcSpringbootEnableServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RpcSpringbootEnableServerApplication.class, args);
	}
}
