package cc.essence.abstraction.client.options

@Suppress("INAPPLICABLE_JVM_NAME", "unused")
interface Options {
    @get:JvmName("essence\$sensitivity")
    @set:JvmName("essence\$sensitivity")
    var sensitivity: Float

    @get:JvmName("essence\$viewDistance")
    @set:JvmName("essence\$viewDistance")
    var viewDistance: Int

    @get:JvmName("essence\$bobView")
    @set:JvmName("essence\$bobView")
    var bobView: Boolean

    @get:JvmName("essence\$fbo")
    @set:JvmName("essence\$fbo")
    var fbo: Boolean

    @get:JvmName("essence\$maxFramerate")
    @set:JvmName("essence\$maxFramerate")
    var maxFramerate: Int

    @get:JvmName("essence\$fancyGraphics")
    @set:JvmName("essence\$fancyGraphics")
    var fancyGraphics: Boolean

    @get:JvmName("essence\$ao")
    @set:JvmName("essence\$ao")
    var ao: Int

    @get:JvmName("essence\$resourcePacks")
    @set:JvmName("essence\$resourcePacks")
    var resourcePacks: List<String>

    @get:JvmName("essence\$incompatibleResourcePacks")
    @set:JvmName("essence\$incompatibleResourcePacks")
    var incompatibleResourcePacks: List<String>

    @get:JvmName("essence\$fullscreen")
    @set:JvmName("essence\$fullscreen")
    var fullscreen: Boolean

    @get:JvmName("essence\$vsync")
    @set:JvmName("essence\$vsync")
    var vsync: Boolean

    @get:JvmName("essence\$vbo")
    @set:JvmName("essence\$vbo")
    var vbo: Boolean

    @get:JvmName("essence\$advancedItemTooltips")
    @set:JvmName("essence\$advancedItemTooltips")
    var advancedItemTooltips: Boolean

    @get:JvmName("essence\$pauseOnLostFocus")
    @set:JvmName("essence\$pauseOnLostFocus")
    var pauseOnLostFocus: Boolean

    @get:JvmName("essence\$mipmapLevels")
    @set:JvmName("essence\$mipmapLevels")
    var mipmapLevels: Int

    @get:JvmName("essence\$entityShadows")
    @set:JvmName("essence\$entityShadows")
    var entityShadows: Boolean

    @get:JvmName("essence\$forwardKey")
    @set:JvmName("essence\$forwardKey")
    var forwardKey: KeyBind

    @get:JvmName("essence\$leftKey")
    @set:JvmName("essence\$leftKey")
    var leftKey: KeyBind

    @get:JvmName("essence\$backKey")
    @set:JvmName("essence\$backKey")
    var backKey: KeyBind

    @get:JvmName("essence\$rightKey")
    @set:JvmName("essence\$rightKey")
    var rightKey: KeyBind

    @get:JvmName("essence\$jumpKey")
    @set:JvmName("essence\$jumpKey")
    var jumpKey: KeyBind

    @get:JvmName("essence\$sneakKey")
    @set:JvmName("essence\$sneakKey")
    var sneakKey: KeyBind

    @get:JvmName("essence\$sprintKey")
    @set:JvmName("essence\$sprintKey")
    var sprintKey: KeyBind

    @get:JvmName("essence\$inventoryKey")
    @set:JvmName("essence\$inventoryKey")
    var inventoryKey: KeyBind

    @get:JvmName("essence\$useKey")
    @set:JvmName("essence\$useKey")
    var useKey: KeyBind

    @get:JvmName("essence\$dropKey")
    @set:JvmName("essence\$dropKey")
    var dropKey: KeyBind

    @get:JvmName("essence\$attackKey")
    @set:JvmName("essence\$attackKey")
    var attackKey: KeyBind

    @get:JvmName("essence\$pickItemKey")
    @set:JvmName("essence\$pickItemKey")
    var pickItemKey: KeyBind

    @get:JvmName("essence\$chatKey")
    @set:JvmName("essence\$chatKey")
    var chatKey: KeyBind

    @get:JvmName("essence\$playerListKey")
    @set:JvmName("essence\$playerListKey")
    var playerListKey: KeyBind

    @get:JvmName("essence\$commandKey")
    @set:JvmName("essence\$commandKey")
    var commandKey: KeyBind

    @get:JvmName("essence\$screenshotKey")
    @set:JvmName("essence\$screenshotKey")
    var screenshotKey: KeyBind

    @get:JvmName("essence\$togglePerspectiveKey")
    @set:JvmName("essence\$togglePerspectiveKey")
    var togglePerspectiveKey: KeyBind

    @get:JvmName("essence\$smoothCameraKey")
    @set:JvmName("essence\$smoothCameraKey")
    var smoothCameraKey: KeyBind

    @get:JvmName("essence\$fullscreenKey")
    @set:JvmName("essence\$fullscreenKey")
    var fullscreenKey: KeyBind

    @get:JvmName("essence\$hudHidden")
    @set:JvmName("essence\$hudHidden")
    var hudHidden: Boolean

    @get:JvmName("essence\$perspective")
    @set:JvmName("essence\$perspective")
    var perspective: Int

    @get:JvmName("essence\$debugEnabled")
    @set:JvmName("essence\$debugEnabled")
    var debugEnabled: Boolean

    @get:JvmName("essence\$debugProfilerEnabled")
    @set:JvmName("essence\$debugProfilerEnabled")
    var debugProfilerEnabled: Boolean

    @get:JvmName("essence\$debugFpsEnabled")
    @set:JvmName("essence\$debugFpsEnabled")
    var debugFpsEnabled: Boolean

    @get:JvmName("essence\$lastServer")
    @set:JvmName("essence\$lastServer")
    var lastServer: String

    @get:JvmName("essence\$smoothCameraEnabled")
    @set:JvmName("essence\$smoothCameraEnabled")
    var smoothCameraEnabled: Boolean

    @get:JvmName("essence\$fov")
    @set:JvmName("essence\$fov")
    var fov: Float

    @get:JvmName("essence\$gamma")
    @set:JvmName("essence\$gamma")
    var gamma: Float

    @get:JvmName("essence\$saturation")
    @set:JvmName("essence\$saturation")
    var saturation: Float

    @get:JvmName("essence\$guiScale")
    @set:JvmName("essence\$guiScale")
    var guiScale: Int

    @get:JvmName("essence\$particle")
    @set:JvmName("essence\$particle")
    var particle: Int
}