package cc.essence.core.config

import cc.essence.core.feature.module.HUDModule
import cc.essence.core.feature.module.ModuleManager
import cc.essence.core.logger
import java.io.*

object Cfg {
    val localDir = System.getProperty("user.home") + "/.essence"

    val cfgDir = "config/"
    val cfgFile = "config/current.bin"

    fun init() {
        val localDir = File(localDir)
        if (!localDir.exists()) {
            logger.info { "Creating local directory" }
            localDir.mkdirs()
        }

        val cfgDir = File(localDir, cfgDir)
        if (!cfgDir.exists()) {
            logger.info { "Creating config directory" }
            cfgDir.mkdirs()
        }

        val cfgFile = File(localDir, cfgFile)
        if (!cfgFile.exists()) {
            logger.info { "Creating config file" }
            cfgFile.createNewFile()
        }
    }

    fun loadModules() {
        val cfgFile = File(localDir, cfgFile)
        if (!cfgFile.exists()) {
            logger.warn { "Config file does not exist" }
            init()
            return
        }

        val byteArray = cfgFile.readBytes()
        val dataInputStream = DataInputStream(ByteArrayInputStream(byteArray))

        while (dataInputStream.available() > 0) {
            val id = dataInputStream.readShort()
            val statusByte = dataInputStream.readByte().toInt()
            val enabled = (statusByte shr 2) and 1 == 1
            val type = statusByte and 3

            val module = ModuleManager[id]
            if (module != null) {
                module.enabled = enabled
                if (type == 1 && module is HUDModule) {
                    module.relativeX = dataInputStream.readDouble()
                    module.relativeY = dataInputStream.readDouble()
                    module.updatePosition()
                }
            } else {
                logger.warn { "Module with ID $id not found" }
            }
        }

        dataInputStream.close()

        logger.info { "Loaded modules" }
    }

    fun saveModules() {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val dataOutputStream = DataOutputStream(byteArrayOutputStream)

        for (value in ModuleManager.modules.values) {
            val id = value.id
            val enabled = if (value.enabled) 1 else 0
            val type = if (value is HUDModule) 1 else 0

            dataOutputStream.writeShort(id.toInt())
            val statusByte = (enabled shl 2) or type
            dataOutputStream.writeByte(statusByte)

            if (type == 1) {
                val hud = value as HUDModule
                dataOutputStream.writeDouble(hud.relativeX)
                dataOutputStream.writeDouble(hud.relativeY)
            }
        }

        val byteArray = byteArrayOutputStream.toByteArray()
        val cfgFile = File(localDir, cfgFile)
        cfgFile.writeBytes(byteArray)

        dataOutputStream.close()
        byteArrayOutputStream.close()

        logger.info { "Saved modules" }
    }
}