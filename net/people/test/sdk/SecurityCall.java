package net.people.test.sdk;

import android.util.Base64;
import android.util.SparseArray;

import net.people.test.sdk.rule.Call;
import net.people.test.sdk.rule.ISecurityStrategy;
import net.people.test.sdk.strategy.SecurityStrategy;

public class SecurityCall<T> implements Call<T> {


    private ServiceMethod serviceMethod;
    private Object[] args;


    public SecurityCall(ServiceMethod serviceMethod, Object[] args) {
        this.serviceMethod = serviceMethod;
        this.args = args;
    }

    @Override
    public Object[] execute() {
        Request request = serviceMethod.toRequest(args);
        SparseArray<ParameterHandle> params = request.params;
        for (int p = 0; p < params.size(); p++) {
            ParameterHandle handle = params.get(p);
            Object encode = handleRequest(handle.isOpetion, handle.isBase64, request, handle);
            args[p] = encode;
        }
        return args;
    }


    private Object handleRequest(boolean isOpetion, boolean isBase64, Request request, ParameterHandle handle) {
        ISecurityStrategy strategy = new SecurityStrategy(request.methodValue);
        Object value = handle.value;
        if (value instanceof String) {
            value = strategy.encrypt((String) value);
            if (!isOpetion) { // 参数无注解不需要任何处理 ，只处理方法上注解的操作
                return value;
            }

            if (isBase64) { // 进行 base 操作
                value = "经过base64: " + Base64.encodeToString(((String) value).getBytes(), Base64.DEFAULT);
            }
            if (handle.isSave) { // 进行数据的保存操作
                String saveKey = handle.getSaveKey();
                value = "已经save  key 为 " + saveKey + "  " + value;
            }
            return value;
        }

        return value;

    }

    private Object createArg(ParameterHandle handle) {
        Object arg = handle.value;
        if (arg instanceof String) {
            arg = arg + "-> +String 1";
        }
        if (arg instanceof Integer) {
            arg = (Integer) arg + 10;
        }
        return arg;
    }


}
