package cc.essence.core.version

import cc.essence.bridge.BridgeRegistry
import cc.essence.core.Core
import cc.essence.core.gl.Cursor
import cc.essence.core.render.skia.Canvas
import cc.essence.core.render.nanovg.NVG
import cc.essence.core.render.skia.SkFrame
import cc.essence.core.resource.ResourceManager
import io.github.humbleui.skija.FontMgr
import org.lwjgl.glfw.GLFW

open class Version(val versionString: String) {
    open fun glInit() {
        Cursor.init()
        NVG.create(NVG.GLType.GL3) { antialias }
        SkFrame.create(BridgeRegistry.display().width, BridgeRegistry.display().height)

        ResourceManager.loadFonts(
            "black"             to "Poppins-Black.ttf",
            "black-italic"      to "Poppins-BlackItalic.ttf",
            "bold"              to "Poppins-Bold.ttf",
            "bold-italic"       to "Poppins-BoldItalic.ttf",
            "extra-bold"        to "Poppins-ExtraBold.ttf",
            "extra-bold-italic" to "Poppins-ExtraBoldItalic.ttf",
            "italic"            to "Poppins-Italic.ttf",
            "regular"           to "Poppins-Regular.ttf",
            "light"             to "Poppins-Light.ttf",
            "light-italic"      to "Poppins-LightItalic.ttf",
            "medium"            to "Poppins-Medium.ttf",
            "medium-italic"     to "Poppins-MediumItalic.ttf",
            "semi-bold"         to "Poppins-SemiBold.ttf",
            "semi-bold-italic"  to "Poppins-SemiBoldItalic.ttf",
            "thin"              to "Poppins-Thin.ttf",
            "thin-italic"       to "Poppins-ThinItalic.ttf",
            "line-awesome"      to "LineAwesome.ttf",
            "material-round"    to "Material-Round.otf",
            "wmfd-bold"    to "WixMadeforDisplay-Bold.ttf",
            "wmfd-ebold"    to "WixMadeforDisplay-ExtraBold.ttf",
            "wmfd-medium"    to "WixMadeforDisplay-Medium.ttf",
            "wmfd-regular"    to "WixMadeforDisplay-Regular.ttf",
            "wmfd-semi-bold"    to "WixMadeforDisplay-SemiBold.ttf",
        )
        
        ResourceManager.loadImages(
            "background" to "background.png",
            "background_blur" to "background_blur.png",
            "logo" to "logo.png",
        )
    }
}