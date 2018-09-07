package net.people.ocr.sec;

import net.people.ocr.User;

import java.lang.reflect.Type;

public class SImpl implements IService {

    @Override
    public User login(String un, String pwd, Type type) {
        User user = new User();
        user.name = un;
        user.age = pwd;
        return user;
    }

    @Override
    public String aes(String value) {
        return value;
    }

    @Override
    public String aesbase(String value) {
        return value;
    }

    @Override
    public String aessave(String value) {
        return value;
    }

    @Override
    public String aessavebase(String value) {
        return value;
    }

    @Override
    public String des(String value) {
        return value;
    }

    @Override
    public String desbase(String value) {
        return value;
    }

    @Override
    public String dessave(String value) {
        return value;
    }

    @Override
    public String dessavebase(String value) {
        return value;
    }


}
