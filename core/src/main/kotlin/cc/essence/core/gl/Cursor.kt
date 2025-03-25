package cc.essence.core.gl

import org.lwjgl.glfw.GLFW

//NOTE: This more or less works but sometimes flickers, maybe some sort of lifecycle implementation is needed
object Cursor {
    const val ARROW = GLFW.GLFW_ARROW_CURSOR
    const val IBEAM = GLFW.GLFW_IBEAM_CURSOR
    const val CROSSHAIR = GLFW.GLFW_CROSSHAIR_CURSOR
    const val POINTING_HAND = GLFW.GLFW_POINTING_HAND_CURSOR
    const val RESIZE_EW = GLFW.GLFW_RESIZE_EW_CURSOR
    const val RESIZE_NS = GLFW.GLFW_RESIZE_NS_CURSOR
    const val RESIZE_NWSE = GLFW.GLFW_RESIZE_NWSE_CURSOR
    const val RESIZE_NESW = GLFW.GLFW_RESIZE_NESW_CURSOR
    const val RESIZE_ALL = GLFW.GLFW_RESIZE_ALL_CURSOR
    const val NOT_ALLOWED = GLFW.GLFW_NOT_ALLOWED_CURSOR
    private val cursors = mutableMapOf<Int, Long>()

    private var current = ARROW

    fun init() {
        cursors[ARROW] = GLFW.glfwCreateStandardCursor(ARROW)
        cursors[IBEAM] = GLFW.glfwCreateStandardCursor(IBEAM)
        cursors[CROSSHAIR] = GLFW.glfwCreateStandardCursor(CROSSHAIR)
        cursors[POINTING_HAND] = GLFW.glfwCreateStandardCursor(POINTING_HAND)
        cursors[RESIZE_EW] = GLFW.glfwCreateStandardCursor(RESIZE_EW)
        cursors[RESIZE_NS] = GLFW.glfwCreateStandardCursor(RESIZE_NS)
        cursors[RESIZE_NWSE] = GLFW.glfwCreateStandardCursor(RESIZE_NWSE)
        cursors[RESIZE_NESW] = GLFW.glfwCreateStandardCursor(RESIZE_NESW)
        cursors[RESIZE_ALL] = GLFW.glfwCreateStandardCursor(RESIZE_ALL)
        cursors[NOT_ALLOWED] = GLFW.glfwCreateStandardCursor(NOT_ALLOWED)
    }

    @JvmStatic
    fun set(cursor: Cursor.() -> Int) {
        val c = cursor(this)
//        GLFW.glfwSetCursor(GLFW.glfwGetCurrentContext(), cursors[c] ?: error("Cursor not found"))
        current = c
    }

    @JvmStatic
    fun set(cursor: Int) {
//        GLFW.glfwSetCursor(GLFW.glfwGetCurrentContext(), cursors[cursor] ?: error("Cursor not found"))
        current = cursor
    }

    @JvmStatic
    fun destroy() {
        cursors.values.forEach(GLFW::glfwDestroyCursor)
    }

    @JvmStatic
    fun current() = current
}