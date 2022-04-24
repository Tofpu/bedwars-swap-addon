plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "io.tofpu"
version = "1.0.0"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.andrei1058.dev/releases/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
//    compileOnly("com.andrei1058.bedwars:BedWars1058:bedwars-plugin:22.3-SNAPSHOT")
    implementation("com.andrei1058.bedwars:bedwars-api:22.3.4")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.processResources {
    filesMatching("plugin.yml") {
        expand(project.properties)
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}