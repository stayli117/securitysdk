package net.people.test.sdk;

import net.people.test.sdk.rule.Converter;

public class GsonRConverter<T> implements Converter<T> {


//    private Gson gson;
//    private TypeAdapter<T> adapter;

//    public GsonRConverter(Gson gson, TypeAdapter<T> adapter) {
//
////        this.gson = gson;
////        this.adapter = adapter;
//    }


    @Override
    public T covert(T value) {

//        try {
//            value = adapter.fromJson("");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return value;
    }
}
