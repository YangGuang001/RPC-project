package com.yang.rpc;

import com.yang.rpc.client.RpcProxy;
import com.yang.rpc.demo.api.HelloService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RpcSpringbootClientApplication {

	private static ConfigurableApplicationContext applicationContext;

	public static void main(String[] args) {
		 applicationContext = SpringApplication.run(RpcSpringbootClientApplication.class, args);
	}

	@GetMapping("/helloworld")
	public String getHelloWorld() {
		RpcProxy rpcProxy = applicationContext.getBean(RpcProxy.class);
		HelloService helloService = rpcProxy.create(HelloService.class, "v2");
		String result = helloService.hello("Hello ");
		return result;
	}
}
