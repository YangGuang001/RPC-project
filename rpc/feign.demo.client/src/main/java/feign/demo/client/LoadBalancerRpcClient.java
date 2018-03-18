//package feign.demo.client;
//
//import com.netflix.client.ClientException;
//import com.netflix.client.config.IClientConfig;
//import feign.Client;
//import feign.Request;
//import feign.Response;
//
//import java.io.IOException;
//import java.net.URI;
//
///**
// * Created by yz on 2018/3/17.
// */
//public class LoadBalancerRpcClient implements Client {
//    static final Request.Options DEFAULT_OPTIONS = new Request.Options();
//
//    private final Client delegate;
//    private CachingSpringLoadBalancerFactory lbClientFactory;
//    private SpringClientFactory clientFactory;
//
//    //使用负载均衡发起restful调用
//    @Override
//    public Response execute(Request request, Request.Options options) throws IOException {
//        try {
//            URI asUri = URI.create(request.url());
//            String clientName = asUri.getHost();
//            URI uriWithoutHost = cleanUrl(request.url(), clientName);
//            FeignLoadBalancer.RibbonRequest ribbonRequest = new FeignLoadBalancer.RibbonRequest(
//                    this.delegate, request, uriWithoutHost);
//
//            IClientConfig requestConfig = getClientConfig(options, clientName);
//            return lbClient(clientName).executeWithLoadBalancer(ribbonRequest,
//                    requestConfig).toResponse();
//        }
//        catch (ClientException e) {
//            IOException io = findIOException(e);
//            if (io != null) {
//                throw io;
//            }
//            throw new RuntimeException(e);
//        }
//    }
//
//    IClientConfig getClientConfig(Request.Options options, String clientName) {
//        IClientConfig requestConfig;
//        if (options == DEFAULT_OPTIONS) {
//            requestConfig = this.clientFactory.getClientConfig(clientName);
//        } else {
//            requestConfig = new FeignOptionsClientConfig(options);
//        }
//        return requestConfig;
//    }
//
//    private FeignLoadBalancer lbClient(String clientName) {
//        return this.lbClientFactory.create(clientName);
//    }
//
//    static URI cleanUrl(String originalUrl, String host) {
//        return URI.create(originalUrl.replaceFirst(host, ""));
//    }
//}
