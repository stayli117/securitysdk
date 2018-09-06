package net.people.test.sdk.rule;

import java.lang.reflect.Type;

public interface CallAdapter<R, T> {


    Type responseType();


    T adapt(Call<R> call);

    public interface Factory {
    }
}
