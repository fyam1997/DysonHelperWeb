plugins {
    kotlin("js") version "1.4.21"
}

group = "com.fyam"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-js"))
    implementation("org.jetbrains.kotlinx:kotlinx-html-js:0.7.1")
    api("io.kvision:jquery-kotlin:1.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
    implementation("org.jetbrains:kotlin-css:1.0.0-pre.144-kotlin-1.4.30")
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