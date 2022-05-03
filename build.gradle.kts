plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("xyz.jpenilla.run-paper") version "1.0.6"

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

    // depending on the bedwars implementation of the bedwars api
    compileOnly("com.andrei1058.bedwars:bedwars-plugin:22.3-SNAPSHOT")

//    compileOnly("com.andrei1058.bedwars:BedWars1058:bedwars-plugin:22.3-SNAPSHOT")
    implementation("com.andrei1058.bedwars:bedwars-api:22.3.4")
    implementation("org.spongepowered:configurate-yaml:4.1.2")
    implementation("com.github.tofpu:dynamic-message:1.0.2")
    implementation("net.kyori:adventure-api:4.10.1")
    implementation("net.kyori:adventure-platform-bukkit:4.1.0")
    implementation("net.kyori:adventure-text-minimessage:4.10.1")


    // for the multi-action bar support
    implementation("com.github.cryptomorin:XSeries:8.7.1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation("com.github.mockbukkit:MockBukkit:v1.16-SNAPSHOT")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
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
            relocate("com.cryptomorin", "io.tofpu.bedwarsswapaddon.lib" +
                    ".xseries")

            exclude("")

//            relocate("revxrsal", "io.tofpu.speedbridge2.lib.lamp")
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