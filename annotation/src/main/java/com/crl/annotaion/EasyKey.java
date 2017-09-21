package com.crl.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by chairuilong on 2017/9/5.
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface EasyKey {

    String value() default "0x00000000";

    boolean commit() default true;

    boolean apply() default false;

}
