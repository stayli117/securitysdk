package net.people.ocr.sec;

import android.util.Log;

import net.people.test.sdk.Security;
import net.people.test.sdk.rule.Call;

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

        Call<String> login = service.login(userName, pwd);

        Log.e(TAG, "login: " + login.execute(0));
        Log.e(TAG, "login: " + login.execute(1));


    }

    public static String aes(boolean isBase, boolean save, String value) {

        Call<String> call = service.aes(value);
        if (isBase) {
            call = save ? service.aessavebase(value) : service.aesbase(value);
        }
        if (save) {
            call = isBase ? service.aessavebase(value) : service.aessave(value);
        }
        return call.execute(0);


    }

    public static String des(boolean isBase, boolean save, String value) {
        Call<String> call = service.des(value);
        if (isBase) {
            call = save ? service.dessavebase(value) : service.desbase(value);
        }
        if (save) {
            call = isBase ? service.dessavebase(value) : service.dessave(value);
        }
        return call.execute(0);
    }
}
