package com.crl.aptmanager;

import android.content.Context;

/**
 * Created by chairuilong on 2017/9/8.
 */

public class APTManager {

    private static Context mContext;

    public static void init(Context context){
        mContext = context;
    }

    public static Context getContext() {
        return mContext;
    }
}
