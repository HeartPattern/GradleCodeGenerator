plugins {
    kotlin("jvm")
    `maven-publish`
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    api(project(":api-common"))
    api("com.squareup", "kotlinpoet", "1.7.2")
}

publishing{
    publications{
        create<MavenPublication>("maven"){
            artifactId = "api-kotlin"
            from(components["java"])
        }
    }
}