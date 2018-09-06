package net.people.test.sdk;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import net.people.test.sdk.rule.CallAdapter;
import net.people.test.sdk.rule.Converter;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

public class Security {




    private final Map<Method, ServiceMethod> serviceMethodCache = new ConcurrentHashMap<>();

    public <T> T registerService(final Class<T> service) {
        return (T) Proxy.newProxyInstance(service.getClassLoader()
                , new Class<?>[]{service},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        ServiceMethod serviceMethod = loadServiceMethod(method);

                        SecurityCall<Object> call = new SecurityCall<>(serviceMethod, args);

                        return serviceMethod.adapter.adapt(call);
                    }
                });
    }


    private ServiceMethod loadServiceMethod(Method method) {

        ServiceMethod result = serviceMethodCache.get(method);

        if (result != null) return result;

        synchronized (serviceMethodCache) {
            result = serviceMethodCache.get(method);
            if (result == null) {
                result = new ServiceMethod.Builder(this, method).build();
                serviceMethodCache.put(method, result);
            }
        }
        return result;
    }

    public CallAdapter callAdapter(Type returnType, Annotation[] annotations) {


        SecurityCallAdapterFactory adapterFactory = new SecurityCallAdapterFactory(new Executor() {


            final Handler handler = new Handler(Looper.getMainLooper());

            @Override
            public void execute(@NonNull Runnable command) {
                handler.post(command);
            }
        });

        return adapterFactory.get(returnType, annotations, this);
    }

//    Gson gson = new Gson();

    public Converter responseBodyConverter(Type responseType, Annotation[] methodAnnotations) {


//        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(responseType));

        return new GsonRConverter<>();
    }
}
