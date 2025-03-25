package cc.essence.abstraction.client.entity

@Suppress("INAPPLICABLE_JVM_NAME")
interface Entity {
    @get:JvmName("essence\$id") val id: Int
    @get:JvmName("essence\$x") val x: Double
    @get:JvmName("essence\$y") val y: Double
    @get:JvmName("essence\$z") val z: Double
    @get:JvmName("essence\$velocityX") val velocityX: Double
    @get:JvmName("essence\$velocityY") val velocityY: Double
    @get:JvmName("essence\$velocityZ") val velocityZ: Double
    @get:JvmName("essence\$yaw") val yaw: Float
    @get:JvmName("essence\$pitch") val pitch: Float
    @get:JvmName("essence\$onGround") val onGround: Boolean
}