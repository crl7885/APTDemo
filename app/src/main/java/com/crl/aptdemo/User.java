package com.crl.aptdemo;


import com.crl.annotaion.EasyKey;
import com.crl.annotaion.EasyPreference;

/**
 * Created by chairuilong on 2017/8/31.
 */

@EasyPreference("test")//设置SharedPreferences 要保存的filename
public class User {

    @EasyKey("name")//要保存key的值
    public String name = "testName";//默认值

    @EasyKey("age")
    public int age = 100;

    @EasyKey("isVip")
    public boolean isVip = true;
}
