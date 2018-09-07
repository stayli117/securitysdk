package net.people.ocr.sec;

import android.util.Log;

import net.people.ocr.User;
import net.people.test.sdk.Security;

public class LoginManager{


    private static IService service;
    private static Security security;

    public static void main() {
        security = new Security();
        init();
        login();
    }


    public static void init() {
        service = security.registerService(IService.class);
    }

    private static final String TAG = "LoginManager";

    public static void login() {

        String userName = "18567677596";
        String pwd = "pwd18567677596";

        User login = service.login(userName, pwd, User.class);

        Log.e(TAG, "login: " + login.name);
        Log.e(TAG, "login: " + login.age);


    }

    public static String aes(boolean isBase, boolean save, String value) {

        String call = service.aes(value);
        if (isBase) {
            call = save ? service.aessavebase(value) : service.aesbase(value);
        }
        if (save) {
            call = isBase ? service.aessavebase(value) : service.aessave(value);
        }
        return call;


    }

    public static String des(boolean isBase, boolean save, String value) {
        String call = service.des(value);
        if (isBase) {
            call = save ? service.dessavebase(value) : service.desbase(value);
        }
        if (save) {
            call = isBase ? service.dessavebase(value) : service.dessave(value);
        }
        return call;
    }
}
