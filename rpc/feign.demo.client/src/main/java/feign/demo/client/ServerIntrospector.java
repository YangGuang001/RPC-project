package feign.demo.client;

import com.netflix.loadbalancer.Server;

import java.util.Map;

/**
 * Created by yz on 2018/3/17.
 */
public interface ServerIntrospector {
    boolean isSecure(Server server);

    Map<String, String> getMetadata(Server server);
}
