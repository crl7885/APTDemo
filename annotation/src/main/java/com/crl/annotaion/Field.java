package com.crl.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by chairuilong on 2017/8/31.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface Field {

    boolean save() default true;

}
