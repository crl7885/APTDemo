package com.crl.aptdemo;

import com.crl.annotaion.DaggerPreferences;
import com.crl.annotaion.Field;

/**
 * Created by chairuilong on 2017/8/31.
 */
@DaggerPreferences("test")
public class User {

    @Field(save = true)
    String test;

}
