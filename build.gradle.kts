plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("xyz.jpenilla.run-paper") version "1.0.6"

}

group = "io.tofpu"
version = "1.1.1-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
    // for brigadier
    maven("https://libraries.minecraft.net")
    maven("https://jitpack.io")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.andrei1058.dev/releases/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.codemc.io/repository/nms/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")

    compileOnly("com.andrei1058.bedwars:bedwars-plugin:23.7-SNAPSHOT")
    compileOnly("com.andrei1058.bedwars:bedwars-api:23.7-SNAPSHOT")

    implementation("org.spongepowered:configurate-yaml:4.1.2")

    implementation("com.github.tofpu:dynamic-message:1.0.6")
    implementation("com.github.Tofpu:message-presenter:1.0.0")

    implementation("net.kyori:adventure-api:4.14.0")
    implementation("net.kyori:adventure-platform-bukkit:4.3.1")
    implementation("net.kyori:adventure-text-minimessage:4.14.0")
    implementation("net.kyori:adventure-text-serializer-legacy:4.14.0")

    implementation("com.github.Revxrsal.Lamp:common:3.0.4")
    implementation("com.github.Revxrsal.Lamp:bukkit:3.0.4")

    implementation("org.bstats:bstats-bukkit:3.0.0")

    // for the multi-action bar support
    implementation("com.github.cryptomorin:XSeries:8.7.1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation("com.github.mockbukkit:MockBukkit:v1.16-SNAPSHOT")
    testImplementation("org.mockito:mockito-core:3.+")

    testImplementation("com.andrei1058.bedwars:bedwars-api:23.7-SNAPSHOT")
    testImplementation("com.andrei1058.bedwars:bedwars-plugin:23.7-SNAPSHOT")

    testImplementation("org.junit.jupiter:junit-jupiter:5.6.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")
}

//sourceSets {
//    main {
//        java {
//            exclude("com/cryptomorin/xseries/*")
//            exclude("com/cryptomorin/xseries/**")
//            include("com/cryptomorin/xseries/ReflectionUtils")
//            include("com/cryptomorin/xseries/messages/**")
//        }
//    }
//}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    test {
        useJUnitPlatform()
    }

    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        mergeServiceFiles()
        classifier = ""

        dependencies {
            relocate("org.spongepowered", "io.tofpu.bedwarsswapaddon.lib.configurate")
            relocate("net.kyori.adventure", "io.tofpu.bedwarsswapaddon.lib.adventure")
            relocate("org.bstats", "io.tofpu.bedwarsswapaddon.lib.bstats")
            relocate("org.yaml.snakeyaml", "io.tofpu.bedwarsswapaddon.lib.snakeyml")
            relocate("io.tofpu.dynamicmessage", "io.tofpu.bedwarsswapaddon.lib.dynamicmessage")
            relocate("io.tofpu.messagepresenter", "io.tofpu.bedwarsswapaddon.lib" +
                    ".messagepresenter")
            relocate("com.cryptomorin", "io.tofpu.bedwarsswapaddon.lib" +
                    ".xseries")
            relocate("revxrsal", "io.tofpu.bedwarsswapaddon.lib.lamp")
            relocate("org.bstats", "io.tofpu.bedwarsswapaddon.lib.bstats")
        }

        exclude("META-INF/**")
    }

    processResources {
        filesMatching("plugin.yml") {
            expand(project.properties)
        }
    }

    getByName<Test>("test") {
        useJUnitPlatform()
    }

    runServer {
        minecraftVersion("1.8.8")
    }
}
