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
    api("org.jetbrains.kotlin","kotlin-gradle-plugin","1.4.21")
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
        create("gradle-code-generator"){
            id = "io.heartpattern.gradle-code-generator-kotlin"
            displayName = "Gradle kotlin code generator plugin"
            description = "Gradle plugin for auto-generating kotlin code"
            implementationClass = "io.heartpattern.gcg.plugin.kotlin.GradleCodeGeneratorKotlinPlugin"
        }
    }
}