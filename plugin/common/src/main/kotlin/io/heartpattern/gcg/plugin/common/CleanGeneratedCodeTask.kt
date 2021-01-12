package io.heartpattern.gcg.plugin.common

import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class CleanGeneratedCodeTask : DefaultTask() {
    @get:InputDirectory
    abstract val targetDirectory: Property<File>

    @TaskAction
    fun cleanGeneratedCode() {
        targetDirectory.get().deleteRecursively()
    }
}