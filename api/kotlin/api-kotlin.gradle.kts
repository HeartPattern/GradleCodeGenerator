plugins {
    kotlin("jvm")
    `maven-publish`
    deploy
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    api(project(":api-common"))
    api("com.squareup", "kotlinpoet", "1.7.2")
}