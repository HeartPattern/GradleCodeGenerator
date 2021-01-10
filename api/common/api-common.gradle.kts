plugins {
    java
    `maven-publish`
}

repositories {
    mavenCentral()
}

publishing{
    publications{
        create<MavenPublication>("maven"){
            artifactId = "api-common"
            from(components["java"])
        }
    }
}