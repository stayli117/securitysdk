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


    private static final String TAG = "Security";

    private final Map<Method, ServiceMethod> serviceMethodCache = new ConcurrentHashMap<>();

    public <T> T registerService(final Class<T> service) {

        if (!service.isInterface()) {
            return createProxy(service);
        }

        throw new IllegalStateException("declarations must not be interfaces ");

//        return (T) Proxy.newProxyInstance(service.getClassLoader()
//                , new Class<?>[]{service},
//                new InvocationHandler() {
//                    @Override
//                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//
//                        ServiceMethod serviceMethod = loadServiceMethod(method);
//
//                        SecurityCall<Object> call = new SecurityCall<>(serviceMethod, args);
//
//                        return serviceMethod.adapter.adapt(this, call);
//
//                    }
//                });
    }

    private <T> T createProxy(final Class<T> service) {

        T t;
        T instance = null;
        try {
            instance = service.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Class<?>[] interfaces = service.getInterfaces();
        if (interfaces.length > 1)
            throw new IllegalStateException("interfaces must not extend other interfaces.");
        final T finalInstance = instance;
        t = (T) Proxy.newProxyInstance(service.getClassLoader(), interfaces, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


                ServiceMethod serviceMethod = loadServiceMethod(method); // 保存注解信息
                SecurityCall<Object> call = new SecurityCall<>(serviceMethod, args);

                return serviceMethod.adapter.adapt(finalInstance, call);
            }
        });


        return t;
    }


    public ServiceMethod loadServiceMethod(Method method) {
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

    public CallAdapter callAdapter(Method method, Annotation[] annotations) {
        SecurityCallAdapterFactory adapterFactory = new SecurityCallAdapterFactory(new Executor() {

            final Handler handler = new Handler(Looper.getMainLooper());

            @Override
            public void execute(@NonNull Runnable command) {
                handler.post(command);
            }
        });

        return adapterFactory.get(method, annotations, this);
    }


    public Converter responseBodyConverter(Type responseType, Annotation[] methodAnnotations) {

        return new GsonRConverter<>();
    }
}
