package com.crl.aptdemo;


import com.crl.annotaion.SPClass;
import com.crl.annotaion.SPField;

/**
 * Created by chairuilong on 2017/8/31.
 */

@SPClass("test")//设置SharedPreferences 要保存的filename
public class User {

    @SPField(key = "name", defaultString = "test_name")
    public String name;

    @SPField(key = "age", defaultInt = 100)
    public int age;

    @SPField(key = "isVip", defaultBoolean = true)
    public boolean isVip;
}
