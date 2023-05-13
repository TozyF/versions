@file:Suppress("UnstableApiUsage")

import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun properties(key: String): Provider<String> = providers.gradleProperty(key)

plugins {
    `java-library`
    alias(libs.plugins.kotlin.jvm)
}

version = properties("version").get()
group = properties("group").get()
description = properties("description").get()

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
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
}
