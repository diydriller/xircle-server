import com.google.protobuf.gradle.id

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

plugins {
    id("com.google.protobuf") version "0.9.4"
}

ext {
    set("springCloudVersion", "2023.0.3")
}

val grpcVersion = "3.19.4"
val grpcKotlinVersion = "1.2.1"
val grpcProtoVersion = "1.44.1"
val grpcReactorVersion = "1.2.4"

dependencies {
    implementation(project(":common"))
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("com.auth0:java-jwt:4.4.0")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("net.devh:grpc-client-spring-boot-starter:2.15.0.RELEASE")
    implementation("io.grpc:grpc-kotlin-stub:$grpcKotlinVersion")
    implementation("io.grpc:grpc-protobuf:$grpcProtoVersion")
    implementation("com.google.protobuf:protobuf-kotlin:$grpcVersion")
    implementation("com.salesforce.servicelibs:reactor-grpc-stub:$grpcReactorVersion")
    implementation("io.grpc:grpc-netty:1.73.0")
    implementation(enforcedPlatform("io.grpc:grpc-bom:1.64.0"))
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootBuildImage>("bootBuildImage") {
    imageName.set("diydriller/${rootProject.name}-${project.name}")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:$grpcVersion"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:$grpcProtoVersion"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:$grpcKotlinVersion:jdk7@jar"
        }
        id("reactor") {
            artifact = "com.salesforce.servicelibs:reactor-grpc:$grpcReactorVersion"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
                id("reactor")
            }
            it.builtins {
                id("kotlin")
            }
        }
    }
}

