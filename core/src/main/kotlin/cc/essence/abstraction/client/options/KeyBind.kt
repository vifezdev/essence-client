package cc.essence.abstraction.client.options

@Suppress("INAPPLICABLE_JVM_NAME")
interface KeyBind {
    @get:JvmName("essence\$key") val key: Int
    @get:JvmName("essence\$pressed")
    @set:JvmName("essence\$pressed")
    var pressed: Boolean
    @get:JvmName("essence\$category") val category: String
    @get:JvmName("essence\$translation") val translation: String

    @JvmName("essence\$wasPressed") fun wasPressed(): Boolean
}