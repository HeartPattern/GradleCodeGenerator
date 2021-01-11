package io.heartpattern.gcg.plugin.common

import org.gradle.api.DomainObjectCollection
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.internal.CollectionCallbackActionDecorator
import org.gradle.api.internal.DefaultDomainObjectSet
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByType

class GradleCodeGeneratorCommonPlugin : Plugin<Project> {
    private val generatorSourceSetNames = HashSet<String>()

    val generatorSourceSets: DomainObjectCollection<SourceSet> = DefaultDomainObjectSet(SourceSet::class.java, CollectionCallbackActionDecorator.NOOP)
    val generateCodeTasks: DomainObjectCollection<GenerateCodeTask> = DefaultDomainObjectSet(GenerateCodeTask::class.java, CollectionCallbackActionDecorator.NOOP)

    override fun apply(target: Project) {
        target.plugins.apply(JavaPlugin::class)
        val sourceSets = target.extensions.getByType<SourceSetContainer>()

        // Setup unified generator dependency
        val generatorImplementation = target.configurations.create("generatorImplementation")
        target.dependencies.add(
            generatorImplementation.name,
            "io.heartpattern.gradle-code-generator:api-common:$apiVersion"
        )

        sourceSets.all {
            // Prevent recursive generator source set creation
            if (name in generatorSourceSetNames)
                return@all

            // Add src dir to target source set
            java.srcDir(generatedSourceSetDir.format(name))

            // Create generator source set
            val generatorSourceSet = target.createGeneratorSourceSet(this)

            // Setup dependency
            target.configurations.getByName(generatorSourceSet.implementationConfigurationName).extendsFrom(generatorImplementation)

            // Create generator task
            val generateCodeTask = target.createGeneratorTask(generatorSourceSet, this)

            generatorSourceSets.add(generatorSourceSet)
            generateCodeTasks.add(generateCodeTask)
        }
    }

    private fun Project.createGeneratorSourceSet(targetSet: SourceSet): SourceSet {
        // Register generator source set
        val sourceSetName = generatorSourceSetName.format(targetSet.name)
        generatorSourceSetNames.add(sourceSetName)

        // Create generate source set
        return extensions.getByType<SourceSetContainer>().create(sourceSetName)
    }

    private fun Project.createGeneratorTask(generatorSourceSet: SourceSet, targetSourceSet: SourceSet): GenerateCodeTask {
        // Create generator task
        return tasks.create<GenerateCodeTask>(generatorTaskName.format(targetSourceSet.name.upperFirst())) {
            generatingSourceSetName.set(generatorSourceSet.name)
            targetSourceSetName.set(targetSourceSet.name)
        }
    }

    private fun String.upperFirst(): String{
        return if(isEmpty()) this else first().toUpperCase() + substring(1)
    }
}