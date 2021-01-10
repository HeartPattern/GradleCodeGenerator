package io.heartpattern.gcg.example.kotlin.generator

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import io.heartpattern.gcg.api.Generator
import io.heartpattern.gcg.api.kotlin.KotlinCodeGenerator

@Generator
class ExampleGenerator : KotlinCodeGenerator {
    override fun generateKotlin(): Collection<FileSpec> {
        return listOf(
            FileSpec.builder("io.heartpattern.gcg.example.kotlin", "GeneratedCode")
                .addFunction(
                    FunSpec.builder("printHello")
                        .addParameter("name", String::class)
                        .addStatement("printHelloDelegate(name)")
                        .build()
                )
                .build()
        )
    }
}