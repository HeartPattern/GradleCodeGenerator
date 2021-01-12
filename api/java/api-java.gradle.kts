plugins {
    `java-library`
    `maven-publish`
    deploy
}

repositories{
    mavenLocal()
    mavenCentral()
}

dependencies{
    api("com.squareup","javapoet","1.13.0")
    api(project(":api-common"))
}