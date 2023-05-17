@file:Suppress("UnstableApiUsage")

import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun properties(key: String): Provider<String> = providers.gradleProperty(key)

plugins {
    `java-library`
    `maven-publish`
    signing
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.dokka)
}

version = properties("version").get()
group = properties("group").get()
description = properties("description").get()

testing {
    suites {
        named<JvmTestSuite>("test") {
            useKotlinTest(libs.versions.kotlin)
        }
    }
}

kotlin {
    explicitApi()
    jvmToolchain(17)
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = JvmTarget.JVM_17.target
        }
    }

    register<Jar>("sourcesJar") {
        from(sourceSets.main.map { it.allSource })
        archiveClassifier = "sources"
    }

    register<Jar>("dokkaJavadocJar") {
        dependsOn(dokkaJavadoc)
        from(dokkaJavadoc.flatMap { it.outputDirectory })
        archiveClassifier = "javadoc"
    }
}

signing {
    sign(publishing.publications)
}

publishing {
    repositories {
        maven {
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = properties("sonatype.user").get()
                username = properties("sonatype.password").get()
            }
        }
    }

    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["dokkaJavadocJar"])
            pom {
                description = project.description
                url = properties("url")
                licenses {
                    license {
                        name = "Apache License 2.0"
                        url = "https://github.com/TozyF/versions/blob/main/LICENSE"
                        distribution = "repo"
                    }
                }
                developers {
                    developer {
                        id = "tozyf"
                        name = "Tozy Fullbuster"
                        url = "https://github.com/TozyF"
                    }
                }
                scm {
                    url = properties("url")
                }
            }
        }
    }
}
