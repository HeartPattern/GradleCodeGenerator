package io.heartpattern.gcg.plugin

import io.heartpattern.gcg.api.CodeGenerator
import io.heartpattern.gcg.api.Generator
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.FileTree
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*
import org.gradle.kotlin.dsl.getByType
import org.reflections.Reflections
import org.reflections.scanners.TypeAnnotationsScanner
import org.reflections.util.ConfigurationBuilder
import java.io.File
import java.net.URLClassLoader
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.jvm.jvmName

abstract class GenerateCodeTask : DefaultTask() {
    @get:Input
    abstract val generatingSourceSetName: Property<String>

    @get:Input
    abstract val targetSourceSetName: Property<String>

    @get:InputFiles
    val sourceDir: FileTree
        get() = generatingSourceSet.allSource.asFileTree

    @get:OutputDirectory
    val outputDir: File
        get() = File(project.projectDir, generatedSourceSetDir.format(targetSourceSetName.get()))

    val generatingSourceSet by lazy {
        project.extensions.getByType<SourceSetContainer>().getByName(generatingSourceSetName.get())
    }

    val targetSourceSet by lazy {
        project.extensions.getByType<SourceSetContainer>().getByName(targetSourceSetName.get())
    }

    @TaskAction
    fun generate() {
        val targetUrls = generatingSourceSet
            .output
            .files
            .filter { it.exists() }
            .map { it.toURI().toURL() }
            .toTypedArray()

        val dependencies = generatingSourceSet.compileClasspath.files
            .map { it.toURI().toURL() }
            .toTypedArray()

        val contextualClassLoader = Thread.currentThread().contextClassLoader

        val loader = URLClassLoader(targetUrls + dependencies, contextualClassLoader)

        Reflections(
            ConfigurationBuilder()
                .addClassLoaders(loader, contextualClassLoader)
                .addUrls(*loader.urLs)
                .addScanners(TypeAnnotationsScanner())
        ).getTypesAnnotatedWith(Generator::class.java).forEach { type ->
            generate(type.kotlin)
        }
    }

    private fun generate(type: KClass<*>) {
        val generator = type.objectInstance ?: type.createInstance()

        if (generator !is CodeGenerator)
            throw GradleException("Class marked with @Generator does not implements CodeGenerator")

        val result = try {
            logger.info("Generate ${type.jvmName}")
            generator.generate()
        } catch (e: Throwable) {
            throw GradleException("Exception thrown while generating code from ${type.jvmName}", e)
        }

        result.forEach { code ->
            val targetFile = File(
                outputDir,
                "${code.packageName.replace('.', File.separatorChar)}${File.separatorChar}${code.fileName}"
            )
            targetFile.parentFile.mkdirs()
            targetFile.writeText(code.content)
        }
    }
}