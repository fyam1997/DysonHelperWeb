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