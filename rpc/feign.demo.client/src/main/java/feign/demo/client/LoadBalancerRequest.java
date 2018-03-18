package feign.demo.client;

/**
 * Created by yz on 2018/3/17.
 */
public interface LoadBalancerRequest<T> {
    public T apply(ServiceInstance instance) throws Exception;
}
