import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.allopen")
    id("org.jetbrains.kotlin.kapt")
    kotlin("plugin.serialization")
//    id("com.google.devtools.ksp") version "2.1.0-1.0.29"
    id("org.sonarqube")
    id("com.github.johnrengelman.shadow")
    id("com.google.cloud.tools.jib")
    id("io.micronaut.application")
    id("io.micronaut.test-resources")
    id("io.micronaut.aot")
    jacoco
}


micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(false)
        annotations("de.sambalmueslie.openevent.*")
    }
    aot {
        optimizeServiceLoading.set(false)
        convertYamlToJava.set(false)
        precomputeOperations.set(true)
        cacheEnvironment.set(true)
        optimizeClassLoading.set(true)
        deduceEnvironment.set(true)
        optimizeNetty.set(true)
        configurationProperties.put("micronaut.security.jwks.enabled", "false")
        configurationProperties.put("micronaut.security.openid-configuration.enabled", "false")
    }
}


dependencies {
    implementation("ch.qos.logback:logback-classic:1.5.15")
    runtimeOnly("org.yaml:snakeyaml")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.4")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.11.4")
    testImplementation("io.mockk:mockk:1.13.14")

    // jackson
    kapt("io.micronaut.serde:micronaut-serde-processor")
    implementation("io.micronaut:micronaut-jackson-databind")
//    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

    // http
    implementation("io.micronaut:micronaut-http-client")

    // validation
    implementation("jakarta.validation:jakarta.validation-api")
    kapt("io.micronaut.validation:micronaut-validation-processor")
    implementation("io.micronaut.validation:micronaut-validation")

    // openapi
    kapt("io.micronaut.openapi:micronaut-openapi")
    implementation("io.swagger.core.v3:swagger-annotations")

    // security
    kapt("io.micronaut.security:micronaut-security-annotations")
    implementation("io.micronaut.security:micronaut-security")
    implementation("io.micronaut.security:micronaut-security-jwt")
    implementation("io.micronaut.security:micronaut-security-oauth2")
    aotPlugins("io.micronaut.security:micronaut-security-aot:4.11.2")

    // kotlin
    implementation("io.micronaut.kotlin:micronaut-kotlin-extension-functions")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("org.jetbrains.kotlin:kotlin-reflect:2.1.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.1.0")

    // caching
    implementation("io.micronaut.cache:micronaut-cache-caffeine")

    // coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:1.10.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.10.1")
    // reactor
    implementation("io.micronaut.reactor:micronaut-reactor")
    implementation("io.micronaut.reactor:micronaut-reactor-http-client")
    // data
    kapt("io.micronaut.data:micronaut-data-processor")
    implementation("io.micronaut.data:micronaut-data-jdbc")
    implementation("io.micronaut.flyway:micronaut-flyway")
    runtimeOnly("org.flywaydb:flyway-database-postgresql")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    runtimeOnly("org.postgresql:postgresql")

    // velocity
    implementation("org.apache.velocity:velocity-engine-core:2.4.1")
    implementation("org.apache.velocity.tools:velocity-tools-generic:3.1")

    // FOP
    implementation("org.apache.xmlgraphics:fop:2.10")
    implementation("org.apache.xmlgraphics:xmlgraphics-commons:2.10")


    // qrcode
    implementation("com.google.zxing:core:3.5.3")
    implementation("com.google.zxing:javase:3.5.3")

    // POI
    implementation("org.apache.poi:poi:5.3.0")
    implementation("org.apache.poi:poi-ooxml:5.3.0")

    // mail
    implementation("org.simplejavamail:simple-java-mail:8.12.4")
    implementation("org.simplejavamail:batch-module:8.12.4")
    implementation("org.simplejavamail:authenticated-socks-module:8.12.4")

    // test
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:testcontainers")
    testImplementation("org.opensearch:opensearch-testcontainers:2.1.2")
    testImplementation("io.micronaut.test:micronaut-test-rest-assured")
    testImplementation("io.fusionauth:fusionauth-jwt:5.3.3")

    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("jakarta.persistence:jakarta.persistence-api:3.2.0")

    // tracing
    implementation("io.micronaut.tracing:micronaut-tracing-jaeger")
    // opensearch
    implementation("com.jillesvangurp:search-client:2.3.4")

    // jsoup
    implementation("org.jsoup:jsoup:1.18.3")
    // biweekly
    implementation("net.sf.biweekly:biweekly:0.6.8")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks {
    compileKotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    compileTestKotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }
}



tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}
tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        csv.required.set(false)
    }
}
jacoco {
    toolVersion = "0.8.11"
}

application {
    mainClass.set("de.sambalmueslie.openevent.OpenEventApplication")
}

jib {
    from.image = "eclipse-temurin:21-jre-alpine"
    to {
        image = "iee1394/open-event"
        tags = setOf(version.toString(), "latest")
    }
    container.creationTime.set("USE_CURRENT_TIMESTAMP")
}