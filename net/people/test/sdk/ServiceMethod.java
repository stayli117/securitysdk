package net.people.test.sdk;

import net.people.test.sdk.annotation.BASE64;
import net.people.test.sdk.annotation.Measure;
import net.people.test.sdk.annotation.Save;
import net.people.test.sdk.rule.CallAdapter;
import net.people.test.sdk.rule.Converter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class ServiceMethod {

    private String methodName;
    private String methodValue;
    private Annotation[][] parameterAnnotationsArray;
    public CallAdapter adapter;
    private Type[] parameterTypes;
    private Converter converter;
    private ParameterHandle<Object>[] handles;

    ServiceMethod(Builder builder) {
        methodName = builder.methodName;
        methodValue = builder.methodValue;
        adapter = builder.callAdapter;
        parameterAnnotationsArray = builder.parameterAnnotationsArray;
        parameterTypes = builder.parameterTypes;
        converter = builder.converter;
        handles = builder.parameterHandles;
    }

    private static final String TAG = "ServiceMethod";

    public Request toRequest(Object[] args) {
        Request.Builder builder = new Request.Builder(methodName, methodValue);
        int argumentCount = args != null ? args.length : 0;
        if (argumentCount != handles.length) throw new IllegalStateException();
        for (int i = 0; i < argumentCount; i++) {
            handles[i].apply(i, builder, args[i]);
        }

        return builder.build();
    }

    static final class Builder {

        private Security security;
        private final Annotation[] methodAnnotations;
        private final Annotation[][] parameterAnnotationsArray;
        private final Type[] parameterTypes;
        private String methodName;
        private Method method;
        private String methodValue;
        private CallAdapter callAdapter;
        private Converter converter;
        Type responseType;
        private ParameterHandle[] parameterHandles;

        public Builder(Security security, Method method) {
            this.security = security;
            this.method = method;
            methodAnnotations = method.getAnnotations();

            parameterTypes = method.getGenericParameterTypes();
            parameterAnnotationsArray = method.getParameterAnnotations();

        }

        private CallAdapter createCallAdapter(Method method) {

            Type returnType = method.getGenericReturnType();

            if (returnType == Void.class) throw new IllegalStateException("设置返回值");

            Annotation[] annotations = method.getAnnotations();


            return security.callAdapter(method, annotations);
        }

        public ServiceMethod build() {
            callAdapter = createCallAdapter(method);
            converter = createResponseConverter(method);
            for (Annotation annotation : methodAnnotations) {
                parseMethodAnnotation(annotation);
            }

            int parameterCount = parameterAnnotationsArray.length;
            parameterHandles = new ParameterHandle[parameterCount];

            for (int i = 0; i < parameterCount; i++) {
                Type type = parameterTypes[i];
                if (Utils.hasUnresolvableType(type)) throw new IllegalStateException();

                Annotation[] annotations = parameterAnnotationsArray[i];

                parameterHandles[i] = parseParamter(i, type, annotations);

            }
            return new ServiceMethod(this);
        }

        private ParameterHandle<Object> parseParamter(int i, Type type, Annotation[] annotations) {
            ParameterHandle<Object> result = null;
            if (annotations == null || annotations.length == 0) {
                result = new ParameterHandle.NoSecurity();
                return result;
            }

            ParameterHandle.NoSecurity security = new ParameterHandle.NoSecurity();


            for (Annotation annotation : annotations) {
                result = parseParamterAnnotation(i, type, annotation, security);
            }

            return result;
        }

        // 解析参数注解
        private ParameterHandle<Object> parseParamterAnnotation(int i, Type type, Annotation annotation, ParameterHandle<Object> security) {
            security.isOpetion = true;
            security.index = i;
            if (annotation instanceof BASE64) {
                if (type == String.class) {
                    security.isBase64 = true;
                }
            }
            if (annotation instanceof Save) {
                if (type == String.class) {
                    security.isSave = true;
                    String value = ((Save) annotation).value();
                    security.setSaveKey(value);
                }
            }

            return security;
        }

        private Converter createResponseConverter(Method method) {
            return security.responseBodyConverter(responseType, methodAnnotations);
        }


        // 解析方法注解
        private void parseMethodAnnotation(Annotation annotation) {
            if (annotation instanceof Measure) {
                parseMethodAnnotationAndValue("Measure", ((Measure) annotation).value());
            }


        }

        private void parseMethodAnnotationAndValue(String measure, String value) {
            methodName = measure;
            if (value.isEmpty()) {
                return;
            }
            methodValue = value;
        }
    }
}
