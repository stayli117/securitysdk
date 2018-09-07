package net.people.ocr.sec;

import net.people.ocr.User;
import net.people.test.sdk.annotation.BASE64;
import net.people.test.sdk.annotation.Measure;
import net.people.test.sdk.annotation.Save;

import java.lang.reflect.Type;

public interface IService {

    @Measure("aes")
    User login(@Save("name") String un, String pwd, Type type);


    @Measure("aes")
    String aes(String value);

    @Measure("aes")
    String aesbase(@BASE64 String value);

    @Measure("aes")
    String aessave(@Save("aes") String value);

    @Measure("aes")
    String aessavebase(@BASE64 @Save("aesbase") String value);


    @Measure("des")
    String des(String value);

    @Measure("des")
    String desbase(@BASE64 String value);

    @Measure("aes")
    String dessave(@Save("des") String value);

    @Measure("aes")
    String dessavebase(@BASE64 @Save("desbase") String value);


}
