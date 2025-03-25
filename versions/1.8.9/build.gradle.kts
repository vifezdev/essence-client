plugins {
    id("java")
    kotlin("jvm") version "1.9.20"
    id("fabric-loom") version "1.7-SNAPSHOT"
    id("legacy-looming") version "1.7-SNAPSHOT"
}

val modVersion: String by project
val mavenGroup: String by project
val loaderVerison: String by project

group = mavenGroup
version = modVersion

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://maven.fabricmc.net/")
}

val lwjglVersion = "3.3.1"

val lwjglNatives = Pair(
    System.getProperty("os.name")!!,
    System.getProperty("os.arch")!!
).let { (name, arch) ->
    when {
        arrayOf("Linux", "SunOS", "Unit").any { name.startsWith(it) } ->
            if (arrayOf("arm", "aarch64").any { arch.startsWith(it) })
                "natives-linux${if (arch.contains("64") || arch.startsWith("armv8")) "-arm64" else "-arm32"}"
            else if (arch.startsWith("ppc"))
                "natives-linux-ppc64le"
            else if (arch.startsWith("riscv"))
                "natives-linux-riscv64"
            else
                "natives-linux"
        arrayOf("Mac OS X", "Darwin").any { name.startsWith(it) }     ->
            "natives-macos${if (arch.startsWith("aarch64")) "-arm64" else ""}"
        arrayOf("Windows").any { name.startsWith(it) }                ->
            if (arch.contains("64"))
                "natives-windows${if (arch.startsWith("aarch64")) "-arm64" else ""}"
            else
                "natives-windows-x86"
        else                                                                            ->
            throw Error("Unrecognized or unsupported platform. Please set \"lwjglNatives\" manually")
    }
}

dependencies {
    minecraft("com.mojang:minecraft:1.8.9")
    mappings(legacy.yarn("1.8.9", "535"))

    modImplementation("net.fabricmc:fabric-loader:0.15.11")
    modImplementation("net.fabricmc:fabric-language-kotlin:1.10.14+kotlin.1.9.20")
    modImplementation("net.legacyfabric.legacy-fabric-api:legacy-fabric-api:1.9.4+1.8.9")
    modImplementation("com.github.Zarzelcow:legacy-lwjgl3:78643b2a96")


    implementation(project(":core"))
    implementation("org.javassist:javassist:3.29.2-GA")

    implementation(platform("org.lwjgl:lwjgl-bom:$lwjglVersion"))
    runtimeOnly("org.lwjgl", "lwjgl", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-glfw", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-openal", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-opengl", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-stb", classifier = lwjglNatives)

    implementation("org.joml:joml:1.10.7")

    implementation("io.github.oshai:kotlin-logging-jvm:5.1.4")
    implementation("org.slf4j:slf4j-simple:2.0.3")
}

configurations.all {
    exclude(group = "org.lwjgl.lwjgl")
    exclude(group = "oshi-project")
}

tasks.withType<JavaCompile>().configureEach {
    this.options.encoding = "UTF-8"
}

var mixinsPath = ""

tasks.withType<JavaExec>().configureEach {
    val runtimeClasspath: Configuration = configurations.getByName("runtimeClasspath")
    val resolvedArtifacts: Set<ResolvedArtifact> = runtimeClasspath.resolvedConfiguration.resolvedArtifacts
    val mixinArtifact = resolvedArtifacts.find { it.moduleVersion.id.group == "net.fabricmc" && it.moduleVersion.id.name == "sponge-mixin" }
    if (mixinArtifact != null) {
        println("Found sponge-mixin library at: ${mixinArtifact.file.absolutePath}")
        mixinsPath = mixinArtifact.file.absolutePath
    } else {
        throw StopExecutionException("sponge-mixin library not found.")
    }
    this.jvmArgs(
         "-XX:+AllowEnhancedClassRedefinition",
        "-javaagent:$mixinsPath"
    )
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
