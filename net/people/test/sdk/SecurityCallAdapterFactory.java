package net.people.test.sdk;

import net.people.test.sdk.rule.Call;
import net.people.test.sdk.rule.CallAdapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Executor;

public class SecurityCallAdapterFactory implements CallAdapter.Factory {


    private Executor executor;

    public SecurityCallAdapterFactory(Executor executor) {
        this.executor = executor;
    }

    public CallAdapter get(final Method method, Annotation[] annotations, Security security) {

        return new CallAdapter<Object, Object>() {
            @Override
            public Method method() {
                return method;
            }

            @Override
            public Object adapt(Object finalInstance, Call<Object> call) throws InvocationTargetException, IllegalAccessException {
                ExecutorCallbackCall<Object> calla = new ExecutorCallbackCall<>(executor, call);
                Object[] execute = calla.execute();
                return method.invoke(finalInstance, execute);
            }

        };


    }

    static final class ExecutorCallbackCall<T> implements Call<T> {
        private Executor executor;
        private Call<T> securityCall;

        public ExecutorCallbackCall(Executor executor, Call<T> securityCall) {
            this.executor = executor;
            this.securityCall = securityCall;
        }

        @Override
        public Object[] execute() {
            return securityCall.execute();
        }

    }


}
