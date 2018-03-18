package feign.demo.client;

import java.net.URI;
import java.util.Map;

/**
 * Created by yz on 2018/3/17.
 */
public interface ServiceInstance {

    /**
     * @return the service id as register by the DiscoveryClient
     */
    String getServiceId();

    /**
     * @return the hostname of the registered ServiceInstance
     */
    String getHost();

    /**
     * @return the port of the registered ServiceInstance
     */
    int getPort();

    /**
     * @return if the port of the registered ServiceInstance is https or not
     */
    boolean isSecure();

    /**
     * @return the service uri address
     */
    URI getUri();

    /**
     * @return the key value pair metadata associated with the service instance
     */
    Map<String, String> getMetadata();
}
