plugins{
    java
    `maven-publish`
    id("com.jfrog.bintray")
}

val sourcesJar = tasks.create<Jar>("sourcesJar"){
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}

val javadocJar = tasks.create<Jar>("javadocJar"){
    archiveClassifier.set("javadoc")
    from(tasks.javadoc.get().outputs)

}

publishing{
    publications{
        create<MavenPublication>("maven"){
            artifactId = project.name
            from(components["java"])
            artifact(javadocJar)
            artifact(sourcesJar)
        }
    }
}

bintray{
    user = properties["bintray.username"] as String?
    key = properties["bintray.apikey"] as String?
    setPublications("maven")

    pkg(closureOf<com.jfrog.bintray.gradle.BintrayExtension.PackageConfig>{
        repo = "gradle"
        name = project.name
        setLicenses("MIT")
        vcsUrl = "https://github.com/HeartPattern/GradleCodeGenerator"

        version(closureOf<com.jfrog.bintray.gradle.BintrayExtension.VersionConfig>{
            name = project.version.toString()

            gpg(closureOf<com.jfrog.bintray.gradle.BintrayExtension.GpgConfig>{
                passphrase = properties["bintray.gpg"] as String?
                sign = true
            })
        })
    })
}