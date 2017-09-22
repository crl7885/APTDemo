package com.crl.processor;

import com.squareup.javapoet.TypeName;

/**
 * Created by chairuilong on 2017/9/8.
 */

public class Utils {

    public static String getFieldMethodName(TypeName type,boolean getter){
        String modName = "";
        if (type.equals(TypeName.BOOLEAN)) {
            if (getter) {
                modName = "getBoolean";
            } else {
                modName = "putBoolean";
            }
        } else if (type.equals(TypeName.INT)) {
            if (getter) {
                modName = "getInt";
            } else {
                modName = "putInt";
            }
        } else if (type.equals(TypeName.DOUBLE)) {
            if (getter) {
                modName = "getDouble";
            } else {
                modName = "putDouble";
            }
        } else if (type.equals(TypeName.FLOAT)) {
            if (getter) {
                modName = "getFloat";
            } else {
                modName = "putFloat";
            }
        } else if (type.equals(TypeName.LONG)) {
            if (getter) {
                modName = "getLong";
            } else {
                modName = "putLong";
            }
        } else if (type.equals(TypeName.get(String.class))) {
            if (getter) {
                modName = "getString";
            } else {
                modName = "putString";
            }
        }
        return modName;
    }

    public static Object getDefaultValue(FieldSet set){
        String modName = "";
        TypeName type = set.getTypeName();
        /*
        if (type.equals(TypeName.BOOLEAN)) {
            return set.isDefaultBoolean();
        } else if (type.equals(TypeName.INT)) {
            return set.getDefaultInt();
        } else if (type.equals(TypeName.DOUBLE)) {
            return set.getDefaultDoutble();
        } else if (type.equals(TypeName.FLOAT)) {
            return set.getDefaultFloat();
        } else if (type.equals(TypeName.LONG)) {
            return set.getDefaultLong();
        } else if (type.equals(TypeName.get(String.class))) {
            return set.getDefaultString();
        }
        */
        return modName;
    }

    /**
     *
     * @param string
     * @return
     */
    public static String toUpperCase4Index(String string) {
        char[] methodName = string.toCharArray();
        methodName[0] = toUpperCase(methodName[0]);
        return String.valueOf(methodName);
    }
    /**
     *
     * @param chars
     * @return
     */
    public static char toUpperCase(char chars) {
        if (97 <= chars && chars <= 122) {
            chars ^= 32;
        }
        return chars;
    }

}
