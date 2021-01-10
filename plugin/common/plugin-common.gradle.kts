plugins {
    `kotlin-dsl`
    `maven-publish`
    id("com.gradle.plugin-publish") version "0.12.0"
}

repositories{
    jcenter()
}

dependencies{
    implementation("org.reflections:reflections:0.9.12")
    implementation(project(":api-common"))
    api(gradleApi())
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
            id = "io.heartpattern.gradle-code-generator-common"
            displayName = "Gradle code generator plugin"
            description = "Gradle plugin for auto-generating code"
            implementationClass = "io.heartpattern.gcg.plugin.common.GradleCodeGeneratorCommonPlugin"
        }
    }
}