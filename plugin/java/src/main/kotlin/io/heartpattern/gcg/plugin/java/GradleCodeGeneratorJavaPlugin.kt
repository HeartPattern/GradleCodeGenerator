package io.heartpattern.gcg.plugin.java

import io.heartpattern.gcg.plugin.common.GradleCodeGeneratorCommonPlugin
import io.heartpattern.gcg.plugin.common.apiVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin

class GradleCodeGeneratorJavaPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.plugins.apply(JavaPlugin::class.java)
        val commonPlugin = target.plugins.apply(GradleCodeGeneratorCommonPlugin::class.java)
        commonPlugin.generateCodeTasks.all {
            target.tasks.getByName(targetSourceSet.compileJavaTaskName).dependsOn(this@all)
            this@all.dependsOn(generatingSourceSet.classesTaskName)
        }

        target.dependencies.add("generatorImplementation", "io.heartpattern.gradle-code-generator:api-java:$apiVersion")
    }
}