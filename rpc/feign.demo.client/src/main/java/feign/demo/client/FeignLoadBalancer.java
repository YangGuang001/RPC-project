//package feign.demo.client;
//
//import com.netflix.client.*;
//import com.netflix.client.config.CommonClientConfigKey;
//import com.netflix.client.config.IClientConfig;
//import com.netflix.loadbalancer.ILoadBalancer;
//import com.netflix.loadbalancer.Server;
//import feign.Client;
//import feign.Request;
//import feign.Response;
//import feign.Util;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpRequest;
//
//import java.io.IOException;
//import java.net.URI;
//import java.util.*;
//
//import static feign.demo.client.RibbonUtils.updateToHttpsIfNeeded;
//
///**
// * Created by yz on 2018/3/17.
// */
//public class FeignLoadBalancer extends
//        AbstractLoadBalancerAwareClient<FeignLoadBalancer.RibbonRequest, FeignLoadBalancer.RibbonResponse> implements
//        ServiceInstanceChooser {
//    private final int connectTimeout;
//    private final int readTimeout;
//    private final IClientConfig clientConfig;
//    private final ServerIntrospector serverIntrospector;
//    private final LoadBalancedRetryPolicyFactory loadBalancedRetryPolicyFactory;
//
//    public FeignLoadBalancer(ILoadBalancer lb, IClientConfig clientConfig,
//                             ServerIntrospector serverIntrospector, LoadBalancedRetryPolicyFactory loadBalancedRetryPolicyFactory) {
//        super(lb, clientConfig);
//        this.loadBalancedRetryPolicyFactory = loadBalancedRetryPolicyFactory;
//        this.setRetryHandler(new DefaultLoadBalancerRetryHandler(clientConfig));
//        this.clientConfig = clientConfig;
//        this.connectTimeout = clientConfig.get(CommonClientConfigKey.ConnectTimeout);
//        this.readTimeout = clientConfig.get(CommonClientConfigKey.ReadTimeout);
//        this.serverIntrospector = serverIntrospector;
//    }
//
//    @Override
//    public URI reconstructURIWithServer(Server server, URI original) {
//        URI uri = updateToHttpsIfNeeded(original, this.clientConfig, this.serverIntrospector, server);
//        return super.reconstructURIWithServer(server, uri);
//    }
//
//
//    @Override
//    public ServiceInstance choose(String serviceId) {
//        return new RibbonLoadBalancerClient.RibbonServer(serviceId,
//                this.getLoadBalancer().chooseServer(serviceId));
//    }
//
//    @Override
//    public RequestSpecificRetryHandler getRequestSpecificRetryHandler(RibbonRequest request, IClientConfig requestConfig) {
//        return new RequestSpecificRetryHandler(false, false, this.getRetryHandler(), requestConfig);
//    }
//
//    @Override
//    public RibbonResponse execute(RibbonRequest request, IClientConfig requestConfig) throws Exception {
//        final Request.Options options;
//        if (configOverride != null) {
//            options = new Request.Options(
//                    configOverride.get(CommonClientConfigKey.ConnectTimeout,
//                            this.connectTimeout),
//                    (configOverride.get(CommonClientConfigKey.ReadTimeout,
//                            this.readTimeout)));
//        }
//        else {
//            options = new Request.Options(this.connectTimeout, this.readTimeout);
//        }
//        LoadBalancedRetryPolicy retryPolicy = loadBalancedRetryPolicyFactory.create(this.getClientName(), this);
//        RetryTemplate retryTemplate = new RetryTemplate();
//        retryTemplate.setRetryPolicy(retryPolicy == null ? new NeverRetryPolicy()
//                : new FeignRetryPolicy(request.toHttpRequest(), retryPolicy, this, this.getClientName()));
//        return retryTemplate.execute(new RetryCallback<RibbonResponse, IOException>() {
//            @Override
//            public RibbonResponse doWithRetry(RetryContext retryContext) throws IOException {
//                Request feignRequest = null;
//                //on retries the policy will choose the server and set it in the context
//                //extract the server and update the request being made
//                if(retryContext instanceof LoadBalancedRetryContext) {
//                    ServiceInstance service = ((LoadBalancedRetryContext)retryContext).getServiceInstance();
//                    if(service != null) {
//                        feignRequest = ((RibbonRequest)request.replaceUri(reconstructURIWithServer(new Server(service.getHost(), service.getPort()), request.getUri()))).toRequest();
//                    }
//                }
//                if(feignRequest == null) {
//                    feignRequest = request.toRequest();
//                }
//                Response response = request.client().execute(feignRequest, options);
//                return new RibbonResponse(request.getUri(), response);
//            }
//        });
//    }
//
//    static class RibbonRequest extends ClientRequest implements Cloneable {
//
//        private final Request request;
//        private final Client client;
//
//        RibbonRequest(Client client, Request request, URI uri) {
//            this.client = client;
//            setUri(uri);
//            this.request = toRequest(request);
//        }
//
//        private Request toRequest(Request request) {
//            Map<String, Collection<String>> headers = new LinkedHashMap<>(
//                    request.headers());
//            // Apache client barfs if you set the content length
//            headers.remove(Util.CONTENT_LENGTH);
//            return Request.create(request.method(),getUri().toASCIIString(),headers,request.body(),request.charset());
//        }
//
//        Request toRequest() {
//            return toRequest(this.request);
//        }
//
//        Client client() {
//            return this.client;
//        }
//
//        HttpRequest toHttpRequest() {
//            return new HttpRequest() {
//                @Override
//                public HttpMethod getMethod() {
//                    return HttpMethod.resolve(RibbonRequest.this.toRequest().method());
//                }
//
//                @Override
//                public URI getURI() {
//                    return RibbonRequest.this.getUri();
//                }
//
//                @Override
//                public HttpHeaders getHeaders() {
//                    Map<String, List<String>> headers = new HashMap<String, List<String>>();
//                    Map<String, Collection<String>> feignHeaders = RibbonRequest.this.toRequest().headers();
//                    for(String key : feignHeaders.keySet()) {
//                        headers.put(key, new ArrayList<String>(feignHeaders.get(key)));
//                    }
//                    HttpHeaders httpHeaders = new HttpHeaders();
//                    httpHeaders.putAll(headers);
//                    return httpHeaders;
//                }
//            };
//        }
//
//        @Override
//        public Object clone() {
//            return new RibbonRequest(this.client, this.request, getUri());
//        }
//    }
//
//    static class RibbonResponse implements IResponse {
//
//        private final URI uri;
//        private final Response response;
//
//        RibbonResponse(URI uri, Response response) {
//            this.uri = uri;
//            this.response = response;
//        }
//
//        @Override
//        public Object getPayload() throws ClientException {
//            return this.response.body();
//        }
//
//        @Override
//        public boolean hasPayload() {
//            return this.response.body() != null;
//        }
//
//        @Override
//        public boolean isSuccess() {
//            return this.response.status() == 200;
//        }
//
//        @Override
//        public URI getRequestedURI() {
//            return this.uri;
//        }
//
//        @Override
//        public Map<String, Collection<String>> getHeaders() {
//            return this.response.headers();
//        }
//
//        Response toResponse() {
//            return this.response;
//        }
//
//        @Override
//        public void close() throws IOException {
//            if (this.response != null && this.response.body() != null) {
//                this.response.body().close();
//            }
//        }
//
//    }
//
//}
