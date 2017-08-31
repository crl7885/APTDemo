package com.crl.processor;

import com.crl.annotaion.DaggerPreferences;
import com.crl.annotaion.Field;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

/**
 * Created by chairuilong on 2017/8/31.
 */
@AutoService(Processor.class)
public class PreferencesProcessor extends AbstractProcessor {

    private Elements elementUtils;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new LinkedHashSet<>();
        set.add(DaggerPreferences.class.getCanonicalName());
        return set;
    }


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.println("PreferencesProcessor process");

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(DaggerPreferences.class);
        System.out.println("PreferencesProcessor process elements "+elements.size());
        for (Element element : elements) {
            TypeElement typeElement = (TypeElement) element;
            List<? extends Element> members = elementUtils.getAllMembers(typeElement);
            ArrayList<MethodSpec> methods = new ArrayList<>();
            for (Element item : members) {
                System.out.println("PreferencesProcessor process elements  item instanceof TypeElement");

                Field annotation = item.getAnnotation(Field.class);
                if(annotation == null){
                    continue;
                }
                boolean save = annotation.save();
                TypeName typeName = TypeName.get(item.asType());
                String name = annotation.annotationType().getSimpleName();
                MethodSpec.Builder setBuilder = MethodSpec.methodBuilder("set" + name)
                        .addModifiers(Modifier.PUBLIC)
                        .returns(typeName)
                        .addParameter(typeName, item.getSimpleName().toString())
                        .addStatement("$T.out.println($S)",System.class,"Hello, JavaPoet!")
                        .addStatement(String.format("this.%s = %s",item.getSimpleName().toString(),item.getSimpleName().toString()))
                        .addStatement(String.format("return %s",item.getSimpleName()));
                methods.add(setBuilder.build());
            }
            TypeSpec.Builder builder = TypeSpec.classBuilder("DI" + element.getSimpleName())
                    .superclass(TypeName.get(typeElement.asType()))
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
            for (MethodSpec methodSpec : methods) {
                builder.addMethod(methodSpec);
            }
            JavaFile javaFile = JavaFile.builder(getPackageName(typeElement), builder.build()).build();
            try {
                javaFile.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private String getPackageName(TypeElement type) {
        return elementUtils.getPackageOf(type).getQualifiedName().toString();
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }

}
