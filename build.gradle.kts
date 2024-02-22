plugins {
    id("java")
}

group = "dev.andreasgeorgatos.afkkicker"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.jetbrains:annotations:21.0.3")
    implementation("net.kyori:adventure-api:4.16.0")
    implementation("org.slf4j:slf4j-api:2.0.12")
    implementation("com.google.code.gson:gson:2.8.8")
    testImplementation("org.slf4j:slf4j-simple:2.0.12")
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
}

tasks.test {
    useJUnitPlatform()
}