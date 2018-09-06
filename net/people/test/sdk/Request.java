package net.people.test.sdk;

import android.util.SparseArray;

public class Request {

    public String methodName;
    public String methodValue;

    public SparseArray<ParameterHandle> params;

    Request(Builder builder) {

        methodName = builder.methodName;
        methodValue = builder.methodValue;
        params = builder.sparseArray;
    }

    public static class Builder {
        private String methodName;
        private String methodValue;
        public SparseArray<ParameterHandle> sparseArray;

        public Builder(String methodName, String methodValue) {

            this.methodName = methodName;
            this.methodValue = methodValue;
            sparseArray = new SparseArray<>();
        }

        public Request build() {
            return new Request(this);
        }

        public void addParameterHandle(int p, ParameterHandle<Object> handle) {
            sparseArray.put(p, handle);
        }

    }
}
