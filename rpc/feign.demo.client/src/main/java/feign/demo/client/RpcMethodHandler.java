package feign.demo.client;

import feign.InvocationHandlerFactory;

/**
 * Created by yz on 2018/3/18.
 */
public class RpcMethodHandler implements InvocationHandlerFactory.MethodHandler {
    @Override
    public Object invoke(Object[] argv) throws Throwable {
        return null;
    }
}
