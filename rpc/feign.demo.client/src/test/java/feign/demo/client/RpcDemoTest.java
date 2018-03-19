package feign.demo.client;

import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.config.ConfigurationManager;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.DiscoveryManager;
import com.netflix.discovery.EurekaClientConfig;
import feign.Feign;
import feign.Request;
import feign.Retryer;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.ribbon.RibbonClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by yz on 2018/3/17.
 */
public class RpcDemoTest {

    @Test
    public void TestRpcDemo() throws Exception{
//        UserServiceFeignClient serviceFeignClient = RpcFeign
//                .builder().encoder(new JacksonEncoder()).decoder(new JacksonDecoder())
//                .client(RibbonClient.create())
//                .options(new Request.Options(1000, 3500))
//                .retryer(new Retryer.Default(5000, 5000, 3))
//                .target(UserServiceFeignClient.class, "http://microservice-provider-user");
//        User user = serviceFeignClient.getOwer(1);
//        System.out.println(user.toString());

        ApplicationContext context = new ClassPathXmlApplicationContext("rpc.xml");
        DiscoveryManager.getInstance().setDiscoveryClient((DiscoveryClient)context.getBean("discoveryClient"));
        DiscoveryManager.getInstance().setEurekaClientConfig((EurekaClientConfig)context.getBean("defaultEurekaClientConfig"));
        DiscoveryManager.getInstance().setEurekaInstanceConfig((EurekaInstanceConfig)context.getBean("myDataCenterInstanceConfig"));

        ConfigurationManager.loadPropertiesFromResources("microservice-provider-user.properties");

        UserServiceFeignClient serviceFeignClient = RpcFeign
                .builder().encoder(new JacksonEncoder()).decoder(new JacksonDecoder())
                .client(RibbonClient.create())
                .options(new Request.Options(1000, 3500))
                .retryer(new Retryer.Default(5000, 5000, 3))
                .target(UserServiceFeignClient.class, "http://microservice-provider-user");
        User user = serviceFeignClient.getOwer(1);
        System.out.println(user.toString());
    }
}
