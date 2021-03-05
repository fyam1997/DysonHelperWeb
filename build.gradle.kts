plugins {
    kotlin("js") version "1.4.21"
    kotlin("plugin.serialization") version "1.4.10"
}

group = "com.fyam"
version = "1.0.1"
buildscript {
    repositories { jcenter() }

    dependencies {
        val kotlinVersion = "1.4.10"
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
        classpath(kotlin("serialization", version = kotlinVersion))
    }
}

repositories {
    jcenter()
    mavenCentral()
    maven { url = uri("https://dl.bintray.com/kotlin/kotlin-js-wrappers") }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")

    implementation("org.jetbrains:kotlin-react:16.13.1-pre.113-kotlin-1.4.0")
    implementation("org.jetbrains:kotlin-react-dom:16.13.1-pre.113-kotlin-1.4.0")
    implementation("org.jetbrains:kotlin-styled:1.0.0-pre.113-kotlin-1.4.0")
    implementation("org.jetbrains:kotlin-react-router-dom:5.1.2-pre.113-kotlin-1.4.0")
    implementation("org.jetbrains:kotlin-redux:4.0.0-pre.113-kotlin-1.4.0")
    implementation("org.jetbrains:kotlin-react-redux:5.0.7-pre.113-kotlin-1.4.0")

    implementation(npm("styled-components", "~5.1.1"))
    implementation(npm("inline-style-prefixer", "~6.0.0"))
}

kotlin {
    js(LEGACY) {
        browser {
            browser {
                distribution {
                    directory = file("$projectDir/docs/")
                }
            }
            binaries.executable()
            webpackTask {
                cssSupport.enabled = true
            }
            runTask {
                cssSupport.enabled = true
            }
        }
    }
}