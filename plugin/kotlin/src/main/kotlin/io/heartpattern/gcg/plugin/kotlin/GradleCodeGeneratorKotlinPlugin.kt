package io.heartpattern.gcg.plugin.kotlin

import io.heartpattern.gcg.plugin.GradleCodeGeneratorCommonPlugin
import io.heartpattern.gcg.plugin.apiVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByName
import org.jetbrains.kotlin.gradle.dsl.KotlinSingleJavaTargetExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin

class GradleCodeGeneratorKotlinPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.plugins.apply(KotlinPlatformJvmPlugin::class.java)
        val commonPlugin = target.plugins.apply(GradleCodeGeneratorCommonPlugin::class.java)
        val compilations = target.extensions.getByName<KotlinSingleJavaTargetExtension>("kotlin").target.compilations

        commonPlugin.generateCodeTasks.all {
            compilations
                .find { it.javaSourceSet == targetSourceSet }!!
                .compileKotlinTask
                .dependsOn(this@all)
            this@all.dependsOn(generatingSourceSet.classesTaskName)
        }

        target.dependencies.add("generatorImplementation", "io.heartpattern.gradle-code-generator:api-kotlin:$apiVersion")
    }
}