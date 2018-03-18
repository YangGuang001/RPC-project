package feign.demo.client;

import com.netflix.hystrix.HystrixCommand;
import feign.Contract;
import feign.MethodMetadata;
import rx.Completable;
import rx.Observable;
import rx.Single;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import static feign.Util.resolveLastTypeParameter;

/**
 * Created by yz on 2018/3/16.
 */
public class RpcDelegatingContract implements Contract {
    private final Contract delegate;

    public RpcDelegatingContract(Contract delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<MethodMetadata> parseAndValidatateMetadata(Class<?> targetType) {
        List<MethodMetadata> metadatas = this.delegate.parseAndValidatateMetadata(targetType);

        for (MethodMetadata metadata : metadatas) {
            Type type = metadata.returnType();

            if (type instanceof ParameterizedType && ((ParameterizedType) type).getRawType().equals(HystrixCommand.class)) {
                Type actualType = resolveLastTypeParameter(type, HystrixCommand.class);
                metadata.returnType(actualType);
            } else if (type instanceof ParameterizedType && ((ParameterizedType) type).getRawType().equals(Observable.class)) {
                Type actualType = resolveLastTypeParameter(type, Observable.class);
                metadata.returnType(actualType);
            } else if (type instanceof ParameterizedType && ((ParameterizedType) type).getRawType().equals(Single.class)) {
                Type actualType = resolveLastTypeParameter(type, Single.class);
                metadata.returnType(actualType);
            } else if (type instanceof ParameterizedType && ((ParameterizedType) type).getRawType().equals(Completable.class)) {
                metadata.returnType(void.class);
            }
        }

        return metadatas;
    }
}
