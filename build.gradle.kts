plugins {
    id("org.jetbrains.kotlin.jvm") version "1.7.10"
    id("org.jetbrains.kotlin.kapt") version "1.7.10"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.7.10"
    id("org.sonarqube") version "3.3"
    id("com.google.cloud.tools.jib") version "3.3.0" apply (false)
    id("io.micronaut.application") version "3.6.2" apply (false)
}

repositories {
    mavenCentral()
}

subprojects {

    apply(plugin = "org.jetbrains.kotlin.jvm")

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.10")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.7.10")
        runtimeOnly("ch.qos.logback:logback-classic:1.4.1")

        testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.0")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.0")
        testImplementation("io.mockk:mockk:1.12.0")
    }

    java {
        sourceCompatibility = JavaVersion.toVersion("17")
    }

    tasks {
        compileKotlin {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
        compileTestKotlin {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }


}
