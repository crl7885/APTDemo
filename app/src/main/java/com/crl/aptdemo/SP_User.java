package com.crl.aptdemo;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by chairuilong on 2017/9/5.
 */

public class SP_User {

    private SharedPreferences sp = null;
    private User mUser;
    private static volatile SP_User mInstance = null;

    private SP_User(Context context) {
        sp = context.getSharedPreferences("name", 0);
    }


    public static SP_User getInstance(Context context) {
        if (mInstance == null) {
            synchronized (SP_User.class) {
                mInstance = new SP_User(context);
            }
        }
        return mInstance;
    }


    public String getName(){
        return "";
    }

    public void setName(String name){

    }

    public int getAge(){
        return 0;
    }

    public void setAge(int age){

    }

    public boolean isVip(){
        return false;
    }

    public void setVip(boolean isVip){

    }

    public void clear(){

    }

}
