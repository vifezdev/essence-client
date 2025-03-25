package cc.essence.core.ui.elements.clickgui.page

import cc.essence.core.animation.Ease
import cc.essence.core.animation.Easing
import cc.essence.core.ui.elements.Element

abstract class Page(val name: String, x: Float, y: Float, w: Float, h: Float) : Element(x, y, w, h) {
    val openEase = Ease(Easing.EXPO_IN_OUT) { 400f }
}