package net.people.ocr.an;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by stayli on 2018/3/13.
 */
@Documented
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface ArgsN {
    String value();
}
