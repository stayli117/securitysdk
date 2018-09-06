package net.people.test.sdk;

import android.util.Base64;
import android.util.SparseArray;

import net.people.test.sdk.rule.Call;
import net.people.test.sdk.rule.ISecurityStrategy;
import net.people.test.sdk.strategy.SecurityStrategy;

import java.util.ArrayList;

public class SecurityCall<T> implements Call<T> {


    private ServiceMethod serviceMethod;
    private Object[] args;


    public SecurityCall(ServiceMethod serviceMethod, Object[] args) {
        this.serviceMethod = serviceMethod;
        this.args = args;
    }

    @Override
    public T execute(int i) {
        Request request = serviceMethod.toRequest(args);

        SparseArray<ParameterHandle> params = request.params;

        ArrayList<String> ens = new ArrayList<>();
        for (int p = 0; p < params.size(); p++) {
            ParameterHandle handle = params.get(p);
            String encode = handleRequest(handle.isOpetion, handle.isBase64, request, handle);
            ens.add(encode);
        }
        return (T) ens.get(i);
    }


    private String handleRequest(boolean isOpetion, boolean isBase64, Request request, ParameterHandle handle) {
        ISecurityStrategy strategy = new SecurityStrategy(request.methodValue);
        String encrypt = strategy.encrypt((String) handle.value);
        if (!isOpetion) { // 参数无注解不需要任何处理 ，只处理方法上注解的操作
            return encrypt;
        }



        if (isBase64) { // 进行 base 操作
            encrypt = "经过base64: " + Base64.encodeToString(strategy.encrypt((String) handle.value).getBytes(), Base64.DEFAULT);
        }

        if (handle.isSave) { // 进行数据的保存操作
            String saveKey = handle.getSaveKey();
            encrypt = "已经save  key 为 " + saveKey + "  " + encrypt;
        }




        return encrypt;

    }


}
