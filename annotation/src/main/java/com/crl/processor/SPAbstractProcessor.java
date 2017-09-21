package com.crl.processor;

import com.crl.annotaion.EasyPreference;
import com.crl.annotaion.EasyKey;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import static com.google.auto.common.MoreElements.getPackage;

/**
 * Created by chairuilong on 2017/9/6.
 */
@AutoService(Processor.class)
public class SPAbstractProcessor extends AbstractProcessor {

    private Elements elementUtils;
    private Types typeUtils;
    private Filer filer;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new LinkedHashSet<>();
        set.add(EasyPreference.class.getCanonicalName());

        return set;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        List<JavaFile> javaFiles = getJavaFiles(set, roundEnvironment);
        for (JavaFile file : javaFiles) {
            try {
                file.writeTo(filer);
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
        typeUtils = processingEnv.getTypeUtils();
        filer = processingEnv.getFiler();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }

    private List<JavaFile> getJavaFiles(Set<? extends TypeElement> set, RoundEnvironment env) {
        List<JavaFile> files = new ArrayList<>();
        for (Element element : env.getElementsAnnotatedWith(EasyPreference.class)) {
            TypeElement typeElement = (TypeElement) element;
            EasyPreference annotation = element.getAnnotation(EasyPreference.class);

            String packageName = getPackage(typeElement).getQualifiedName().toString();
            String className = typeElement.getQualifiedName().toString().substring(
                    packageName.length() + 1).replace('.', '$');
            ClassName fromName = ClassName.get(packageName, className);

            TypeSet bindingSet = new TypeSet(TypeName.get(typeElement.asType()),
                    fromName, annotation.value());

            List<FieldSet> fieldSets = parserField(typeElement);
            bindingSet.setFiedlSets(fieldSets);

            JavaFile java = bindingSet.createJava();

            files.add(java);
        }
        return files;
    }

    private List<FieldSet> parserField(TypeElement typeElement) {
        List<? extends Element> members = elementUtils.getAllMembers(typeElement);
        List<FieldSet> fieldSets = new ArrayList<>();
        for (Element element : members) {
            EasyKey field = element.getAnnotation(EasyKey.class);
            if (field == null) {
                continue;
            }

            TypeName typeName = TypeName.get(element.asType());
            String name = element.getSimpleName().toString();
            FieldSet set = new FieldSet(typeName, name, field.value()
                    , field.commit(), field.apply());
            fieldSets.add(set);
        }
        return fieldSets;
    }

}
