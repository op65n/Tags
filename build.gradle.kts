import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
}

repositories {
    mavenCentral()
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.mattstudios.me/artifactory/public/")
}

dependencies {
    implementation("dev.triumphteam:triumph-gui:3.1.3")
    implementation("me.mattstudios.utils:matt-framework:1.4.6")
    implementation("org.tomlj:tomlj:1.1.0")

    implementation("org.spongepowered:configurate-yaml:4.1.2")

    compileOnly("net.luckperms:api:5.4")
    compileOnly("me.clip:placeholderapi:2.11.1")
    compileOnly("io.papermc.paper:paper-api:1.19.2-R0.1-SNAPSHOT")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks {
    withType<JavaCompile> {
        options.encoding = Charsets.UTF_8.name()
    }

    withType<ShadowJar> {
        fun reloc(vararg clazz: String) {
            clazz.forEach { relocate(it, "${project.group}.${rootProject.name}.libs.$it") }
        }

        archiveFileName.set("MCSITags-${project.version}.jar")
    }

    build {
        dependsOn(shadowJar)
    }
}

bukkit {
    name = "MCSITags"
    version = "${project.version}"
    author = "Frcsty"
    main = "${project.group}.${rootProject.name}.TagsPlugin"
    apiVersion = "1.19"
    depend = listOf("LuckPerms")
}