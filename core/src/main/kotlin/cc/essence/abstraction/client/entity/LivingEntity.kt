package cc.essence.abstraction.client.entity

@Suppress("INAPPLICABLE_JVM_NAME")
interface LivingEntity : Entity {
    @JvmName("essence\$getHealth") fun getHealth(): Float
    @JvmName("essence\$getMaxHealth") fun getMaxHealth(): Float
    @JvmName("essence\$isAlive") fun isAlive(): Boolean
}