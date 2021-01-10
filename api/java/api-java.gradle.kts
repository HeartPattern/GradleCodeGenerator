plugins {
    `java-library`
    `maven-publish`
}

repositories{
    mavenLocal()
    mavenCentral()
}

dependencies{
    api("com.squareup","javapoet","1.13.0")
    api(project(":api-common"))
}

publishing{
    publications{
        create<MavenPublication>("maven"){
            artifactId = "api-java"
            from(components["java"])
        }
    }
}