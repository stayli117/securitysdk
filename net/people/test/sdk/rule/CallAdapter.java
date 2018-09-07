package net.people.test.sdk.rule;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface CallAdapter<R, T> {


    Method method();


    R adapt(T finalInstance, Call<T> call) throws InvocationTargetException, IllegalAccessException;

    public interface Factory {
    }
}
