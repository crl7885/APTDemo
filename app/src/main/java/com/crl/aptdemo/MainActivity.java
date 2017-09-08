package com.crl.aptdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    TextView mTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTxt = (TextView) findViewById(R.id.txt_di_textview);
        SPUser spUser = SPUser.get();
        spUser.getName();
        spUser.getAge();
        spUser.getIsVip();

        String format = String.format("name : %s\n age : %d \n isVip : %b", spUser.getName(), spUser.getAge(), spUser.getIsVip());
        mTxt.setText(format);

        spUser.setName("crl");
        spUser.setAge(20);
        spUser.setIsVip(true);

    }
}
