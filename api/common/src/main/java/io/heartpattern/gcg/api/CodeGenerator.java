package io.heartpattern.gcg.api;

import java.util.Collection;

/**
 * Source code generator. Class implementing CodeGenerator and annotated with @Generator is called on generateMain task to generate code.
 */
public interface CodeGenerator {
    /**
     * Generate source code.
     * @return Collection of generated source code
     */
    Collection<Code> generate();
}
