package com.crl.aptdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.crl.annotaion.DIActivity;
import com.crl.annotaion.DIView;


@DIActivity
public class MainActivity extends AppCompatActivity {

    @DIView(R.id.txt_di_textview)
    TextView mTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        DIMainActivity.bindView(this);
        mTxt.setText("hello apt");
    }
}
