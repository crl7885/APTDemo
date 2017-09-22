package com.crl.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;

/**
 * Created by chairuilong on 2017/9/7.
 */

public class TypeSet {

    private final TypeName typeName;
    private final ClassName fromClassName;
    private final String value;
    private final String memberSpName = "mSp";
    private final String className;
    private final ClassName myClass;
    private final String instances = "mInstances";
    private List<FieldSet> fiedlSets = new ArrayList<>();

    public TypeSet(TypeName typeName, ClassName fromClassName, String value) {
        this.typeName = typeName;
        this.fromClassName = fromClassName;
        this.value = value;
        className = "SP" + fromClassName.simpleName();
        myClass = ClassName.get(fromClassName.packageName(), className);
    }

    JavaFile createJava() {
        return JavaFile.builder(fromClassName.packageName(), createType())
                .build();
    }

    public void setFiedlSets(List<FieldSet> fiedlSets) {
        this.fiedlSets = fiedlSets;
    }

    private TypeSpec createType() {


        TypeSpec.Builder result = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC,Modifier.FINAL)
                .superclass(typeName);


        result.addField(createSPField());

        result.addField(createSingle());

        result.addMethod(createPrivateConstructor());

        result.addMethod(createGetInstances());

        for (FieldSet fieldSet : fiedlSets) {
            result.addMethod(createGetField(fieldSet));
            result.addMethod(createSetField(fieldSet));
        }

        result.addMethod(createClear());

        return result.build();
    }

    private FieldSpec createSPField() {
        ClassName spClass = ClassName.get("android.content", "SharedPreferences");
        FieldSpec.Builder builder = FieldSpec.builder(spClass.withoutAnnotations(), memberSpName, Modifier.PRIVATE)
                .initializer("null");
        return builder.build();
    }

    private FieldSpec createSingle() {
        FieldSpec.Builder builder = FieldSpec.builder(myClass.withoutAnnotations(), instances, Modifier.PRIVATE, Modifier.STATIC, Modifier.VOLATILE);
        builder.initializer("null");
        return builder.build();
    }

    private MethodSpec createPrivateConstructor() {
        ClassName contextClass = ClassName.get("android.content", "Context");
        ClassName aptManager = ClassName.get("com.crl.aptmanager", "APTManager");
        CodeBlock.Builder builder = CodeBlock.builder();
        builder.add(
                "$T context = $T.getContext(); \n" +
                        "$L = context.getSharedPreferences($S, 0);"
                , contextClass, aptManager, memberSpName, value);

        MethodSpec.Builder builderMethod = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .addCode(builder.build());
        return builderMethod.build();
    }

    private MethodSpec createGetInstances() {
        CodeBlock.Builder builder = CodeBlock.builder();
        builder.add("if($L == null){\n" +
                "synchronized($T.class){\n" +
                "$L = new $T();\n" +
                "}\n" +
                "}" +
                "return $L;", instances, myClass, instances, myClass, instances);

        MethodSpec.Builder builderMethod = MethodSpec.methodBuilder("get")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(myClass)
                .addCode(builder.build());

        return builderMethod.build();
    }

    private MethodSpec createGetField(FieldSet fieldSet) {

        CodeBlock.Builder builder = CodeBlock.builder();
        String codeFormat = "return $L.$L($S,$L);";
        /*
        if (fieldSet.getTypeName().equals(ClassName.get(String.class))) {
            codeFormat = "return $L.$L($S,$S);";
        }
        */
        builder.add(codeFormat,
                memberSpName, Utils.getFieldMethodName(fieldSet.getTypeName(), true)
                , fieldSet.getKey(), fieldSet.getName());

        MethodSpec.Builder builderMethod = MethodSpec.methodBuilder("get" + Utils.toUpperCase4Index(fieldSet.getName()))
                .returns(getTypeName(fieldSet.getTypeName()))
                .addModifiers(Modifier.PUBLIC)
                .addCode(builder.build());
        return builderMethod.build();
    }

    private TypeName getTypeName(TypeName typeName) throws UnsupportedOperationException {
        TypeName stringType = TypeName.get(String.class);
        if (typeName.equals(stringType)
                || typeName.equals(TypeName.INT)
                || typeName.equals(TypeName.LONG)
                || typeName.equals(TypeName.FLOAT)
                || typeName.equals(TypeName.DOUBLE)
                || typeName.equals(TypeName.BOOLEAN)) {
            return typeName;
        }
        throw new UnsupportedOperationException("Unsuppor type " + typeName);

    }

    private MethodSpec createSetField(FieldSet fieldSet) {

        ClassName edit = ClassName.get("android.content", "SharedPreferences.Editor");

        CodeBlock.Builder builder = CodeBlock.builder()
                .add("$T edit = $L.edit();\n", edit, memberSpName)
                .add("edit.$L($S,$L);\n"
                        , Utils.getFieldMethodName(fieldSet.getTypeName(), false)
                        , fieldSet.getKey()
                        , fieldSet.getName())
                .add("edit.$L;\n", getEditMethod(fieldSet));

        MethodSpec.Builder builderMethod = MethodSpec.methodBuilder("set" + Utils.toUpperCase4Index(fieldSet.getName()))
                .returns(void.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(getTypeName(fieldSet.getTypeName()), fieldSet.getName())
                .addCode(builder.build());
        return builderMethod.build();
    }

    private String getEditMethod(FieldSet fieldSet) {
        if (!fieldSet.isApply()) {
            return "commit()";
        } else {
            return "apply()";
        }
    }

    private MethodSpec createClear() {

        ClassName edit = ClassName.get("android.content", "SharedPreferences.Editor");

        CodeBlock.Builder builder = CodeBlock.builder()
                .add("$T edit = $L.edit();\n", edit, memberSpName)
                .add("edit.clear();\n")
                .add("edit.commit();\n");

        MethodSpec.Builder builderMethod = MethodSpec.methodBuilder("clear")
                .returns(void.class)
                .addModifiers(Modifier.PUBLIC)
                .addCode(builder.build());
        return builderMethod.build();
    }

}
