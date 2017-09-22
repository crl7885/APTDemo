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

    public FieldSet(TypeName typeName, String name, String key, boolean commit, boolean apply) {
        this.typeName = typeName;
        this.name = name;
        this.key = key;
        this.commit = commit;
        this.apply = apply;
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

}
