package io.heartpattern.gcg.api.java;

import com.squareup.javapoet.JavaFile;
import io.heartpattern.gcg.api.Code;
import io.heartpattern.gcg.api.CodeGenerator;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Helper class that generate java source code.
 */
public interface JavaCodeGenerator extends CodeGenerator {
    /**
     * Generate collection of java file using JavaPoet
     * @return Generated JavaFile
     */
    Collection<JavaFile> generateJava();

    @Override
    default Collection<Code> generate() {
        return generateJava().stream()
                .map(java -> new Code(java.packageName, java.typeSpec.name + ".java", java.toString()))
                .collect(Collectors.toList());
    }
}
