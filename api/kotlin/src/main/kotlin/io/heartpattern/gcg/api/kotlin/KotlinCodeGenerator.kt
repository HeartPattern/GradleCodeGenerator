package io.heartpattern.gcg.api.kotlin

import com.squareup.kotlinpoet.FileSpec
import io.heartpattern.gcg.api.Code
import io.heartpattern.gcg.api.CodeGenerator

/**
 * Helper class that generate kotlin source code.
 */
interface KotlinCodeGenerator : CodeGenerator {
    /**
     * Generate collection of [FileSpec] generated by KotlinPoet.
     */
    fun generateKotlin(): Collection<FileSpec>

    override fun generate(): Collection<Code> {
        return generateKotlin().map {
            Code(it.packageName, it.name + ".kt", it.toString())
        }
    }
}