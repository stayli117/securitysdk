package net.people.test.sdk;

import net.people.test.sdk.rule.Call;
import net.people.test.sdk.rule.CallAdapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.Executor;

public class SecurityCallAdapterFactory implements CallAdapter.Factory {


    private Executor executor;

    public SecurityCallAdapterFactory(Executor executor) {
        this.executor = executor;
    }

    public CallAdapter get(Type returnType, Annotation[] annotations, Security security) {

        Class<?> rawType = getRawType(returnType);
        if (rawType != Call.class) return null;

        final Type responseType = Utils.getCallResponseType(returnType);

        return new CallAdapter<Object, Call<?>>() {
            @Override
            public Type responseType() {
                return responseType;
            }

            @Override
            public Call<Object> adapt(Call<Object> call) {
                return new ExecutorCallbackCall<>(executor, call);
            }
        };


    }

    public static Class<?> getRawType(Type returnType) {
        return Utils.getRawType(returnType);
    }

    static final class ExecutorCallbackCall<T> implements Call<T> {
        private Executor executor;
        private Call<T> securityCall;

        public ExecutorCallbackCall(Executor executor, Call<T> securityCall) {
            this.executor = executor;
            this.securityCall = securityCall;
        }

        @Override
        public T execute(int i) {
            return securityCall.execute(i);
        }

    }


}
