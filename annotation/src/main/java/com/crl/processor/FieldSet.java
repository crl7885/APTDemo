package com.crl.processor;

import com.squareup.javapoet.TypeName;

/**
 * Created by chairuilong on 2017/9/8.
 */

public class FieldSet {

    private final TypeName typeName;
    private final String name;
    private final String key;
    private final boolean commit;
    private final boolean apply;
    private final String defaultString;

    private final int defaultInt;

    private final long defaultLong;

    private final float defaultFloat;

    private final double defaultDoutble;

    private final boolean defaultBoolean;

    public FieldSet(TypeName typeName, String name, String key, boolean commit, boolean apply, String defaultString, int defaultInt, long defaultLong, float defaultFloat, double defaultDoutble, boolean defaultBoolean) {
        this.typeName = typeName;
        this.name = name;
        this.key = key;
        this.commit = commit;
        this.apply = apply;
        this.defaultString = defaultString;
        this.defaultInt = defaultInt;
        this.defaultLong = defaultLong;
        this.defaultFloat = defaultFloat;
        this.defaultDoutble = defaultDoutble;
        this.defaultBoolean = defaultBoolean;
    }

    public TypeName getTypeName() {
        return typeName;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public boolean isCommit() {
        return commit;
    }

    public boolean isApply() {
        return apply;
    }

    public String getDefaultString() {
        return defaultString;
    }

    public int getDefaultInt() {
        return defaultInt;
    }

    public long getDefaultLong() {
        return defaultLong;
    }

    public float getDefaultFloat() {
        return defaultFloat;
    }

    public double getDefaultDoutble() {
        return defaultDoutble;
    }

    public boolean isDefaultBoolean() {
        return defaultBoolean;
    }
}
