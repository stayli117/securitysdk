package net.people.ocr.sec;

import net.people.test.sdk.annotation.BASE64;
import net.people.test.sdk.annotation.Measure;
import net.people.test.sdk.annotation.Save;
import net.people.test.sdk.rule.Call;

public interface IService {

    @Measure("aes")
    Call<String> login(@BASE64 String un, String pwd);












    @Measure("aes")
    Call<String> aes(String value);

    @Measure("aes")
    Call<String> aesbase(@BASE64 String value);

    @Measure("aes")
    Call<String> aessave(@Save("aes") String value);

    @Measure("aes")
    Call<String> aessavebase(@BASE64 @Save("aesbase") String value);


    @Measure("des")
    Call<String> des(String value);

    @Measure("des")
    Call<String> desbase(@BASE64 String value);

    @Measure("aes")
    Call<String> dessave(@Save("des") String value);

    @Measure("aes")
    Call<String> dessavebase(@BASE64 @Save("desbase") String value);



}
