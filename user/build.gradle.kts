plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.allopen")
    id("org.sonarqube")
    id("com.google.cloud.tools.jib") version "3.3.0"
    id("io.micronaut.application") version "3.6.2"
}

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("de.sambalmueslie.openevent.user.*")
    }
}


dependencies {
    implementation(project(":common"))
}

application {
    mainClass.set("de.sambalmueslie.openevent.user.UserApplication")
}

