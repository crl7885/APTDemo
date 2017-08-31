package com.crl.processor;


import com.crl.annotaion.HelloWord;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Created by chairuilong on 2017/8/30.
 */
@AutoService(Processor.class)
public class TestProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
//        return super.getSupportedAnnotationTypes();
        return Collections.singleton(HelloWord.class.getCanonicalName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        MethodSpec main = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC,Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class,"args")
                .addStatement("$T.out.println($S)",System.class,"Hello, JavaPoet!").build();

        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC,Modifier.FINAL)
                .addMethod(main).build();
        JavaFile javaFile = JavaFile.builder("com.example.helloworld",helloWorld)
                .build();
        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }
}
