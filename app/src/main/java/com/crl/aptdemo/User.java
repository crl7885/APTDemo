package com.crl.aptdemo;


import com.crl.annotaion.EasyKey;
import com.crl.annotaion.EasyPreference;

/**
 * Created by chairuilong on 2017/8/31.
 */

@EasyPreference("test")//设置SharedPreferences 要保存的filename
public class User {

    @EasyKey("name")
    public String name = "testName";

    @EasyKey("age")
    public int age = 100;

    @EasyKey("isVip")
    public boolean isVip = true;
}
