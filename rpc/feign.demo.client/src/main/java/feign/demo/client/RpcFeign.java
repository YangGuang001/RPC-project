package feign.demo.client;

import feign.*;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yz on 2018/3/16.
 */
public class RpcFeign {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends Feign.Builder {

        private Contract contract = new Contract.Default();

        @Override
        public Builder logLevel(Logger.Level logLevel) {
            return (Builder) super.logLevel(logLevel);
        }

        @Override
        public Builder contract(Contract contract) {
            this.contract = contract;
            return this;
        }

        @Override
        public Builder client(Client client) {
            return (Builder) super.client(client);
        }

        @Override
        public Builder retryer(Retryer retryer) {
            return (Builder) super.retryer(retryer);
        }

        @Override
        public Builder logger(Logger logger) {
            return (Builder) super.logger(logger);
        }

        @Override
        public Builder encoder(Encoder encoder) {
            return (Builder) super.encoder(encoder);
        }

        @Override
        public Builder decoder(Decoder decoder) {
            return (Builder) super.decoder(decoder);
        }

        @Override
        public Builder decode404() {
            return (Builder) super.decode404();
        }

        @Override
        public Builder errorDecoder(ErrorDecoder errorDecoder) {
            return (Builder) super.errorDecoder(errorDecoder);
        }

        @Override
        public Builder options(Request.Options options) {
            return (Builder) super.options(options);
        }

        @Override
        public Builder requestInterceptor(RequestInterceptor requestInterceptor) {
            return (Builder) super.requestInterceptor(requestInterceptor);
        }

        @Override
        public Builder requestInterceptors(Iterable<RequestInterceptor> requestInterceptors) {
            return (Builder) super.requestInterceptors(requestInterceptors);
        }

        @Override
        public Builder invocationHandlerFactory(InvocationHandlerFactory invocationHandlerFactory) {
            return (Builder) super.invocationHandlerFactory(invocationHandlerFactory);
        }

        @Override
        public Feign build() {
            super.invocationHandlerFactory(new RpcInvocationHandler.Factory());
            super.contract(new RpcDelegatingContract(contract));
            //不调用父类的build方法，实现MethodHandler接口，与SynchronousMethodHandler类似，只是rpc调用
            // 直接采用选择器方法， 选择出一个适当的方式去调用，
            //考虑后期做，需要考虑用户用法
            return super.build();
        }

        public <T> T target(Target<T> target, final T fallback) {
            super.invocationHandlerFactory(new InvocationHandlerFactory() {
                @Override
                public InvocationHandler create(Target target, Map<Method, MethodHandler> dispatch) {
                    return new RpcInvocationHandler(target, dispatch, fallback);
                }
            });
            super.contract(new RpcDelegatingContract(contract));
            return super.build().newInstance(target);
        }
    }
}
