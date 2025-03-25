package cc.essence.abstraction.client.gl
@Suppress("INAPPLICABLE_JVM_NAME")
interface Framebuffer {
    val id: Int
    val texture: Int
    val width: Int
    val height: Int
    val texWidth: Int
    val texHeight: Int

    @JvmName("clearFbo") fun clear()
    @JvmName("drawFbo") fun draw(w: Int, h: Int)
    @JvmName("resizeFbo") fun resize(w: Int, h: Int)
    @JvmName("bindFbo") fun bind(viewport: Boolean)
    @JvmName("unbindFbo") fun unbind()
    @JvmName("bindFboTexture") fun bindTexture()
    @JvmName("unbindFboTexture") fun unbindTexture()
    @JvmName("deleteFbo") fun delete()
}