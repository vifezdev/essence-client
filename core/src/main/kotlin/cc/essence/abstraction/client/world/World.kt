package cc.essence.abstraction.client.world

import cc.essence.abstraction.client.entity.Entity

@Suppress("INAPPLICABLE_JVM_NAME")
interface World {
    @get:JvmName("essence\$loadedEntities") val loadedEntities: MutableList<Entity>
}