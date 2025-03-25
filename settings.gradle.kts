rootProject.name = "essence-client"

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://maven.fabricmc.net")
        maven(url = "https://repo.legacyfabric.net/repository/legacyfabric/")
    }

    plugins {
        kotlin("jvm") version "1.9.20"
    }
}

val versions = arrayOf(
    "1.8.9"
).forEach {
    include("versions:$it")
}

include("core")
