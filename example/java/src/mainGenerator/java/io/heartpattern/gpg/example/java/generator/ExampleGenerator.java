package io.heartpattern.gpg.example.java.generator;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import io.heartpattern.gcg.api.Generator;
import io.heartpattern.gcg.api.java.JavaCodeGenerator;

import javax.lang.model.element.Modifier;
import java.util.Collection;
import java.util.Collections;

@Generator
public class ExampleGenerator implements JavaCodeGenerator {
    @Override
    public Collection<JavaFile> generateJava() {
        return Collections.singleton(
                JavaFile.builder(
                        "io.heartpattern.gpg.example.java",
                        TypeSpec.classBuilder("GeneratedClass")
                                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                                .addMethod(
                                        MethodSpec.methodBuilder("printHello")
                                                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                                                .returns(void.class)
                                                .addParameter(String.class, "name")
                                                .addStatement(
                                                        "UsedInGeneratedCode.printHelloDelegate(name)"
                                                )
                                                .build()
                                )
                                .build()
                ).build()
        );
    }
}
