plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `maven-publish`
    id("com.gradle.plugin-publish") version "0.12.0"
}

repositories{
    jcenter()
}

dependencies{
    api(project(":plugin-common"))
}

kotlinDslPluginOptions{
    experimentalWarning.set(false)
}

pluginBundle{
    website = "https://github.com/HeartPattern/GradleCodeGenerator"
    vcsUrl = "https://github.com/HeartPattern/GradleCodeGenerator"
    tags = listOf("generator")
}

gradlePlugin{
    plugins{
        create("gradle-code-generator-java"){
            id = "io.heartpattern.gradle-code-generator-java"
            displayName = "Gradle java code generator plugin"
            description = "Gradle plugin for auto-generating java code"
            implementationClass = "io.heartpattern.gcg.plugin.java.GradleCodeGeneratorJavaPlugin"
        }
    }
}