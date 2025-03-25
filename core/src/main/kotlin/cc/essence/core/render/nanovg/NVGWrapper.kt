package cc.essence.core.render.nanovg

import cc.essence.core.logger
import org.lwjgl.nanovg.*
import java.nio.ByteBuffer
import java.nio.FloatBuffer
import java.nio.IntBuffer
import kotlin.properties.Delegates

private typealias nvgFlag = Int

object NVG {
    const val NVG_PI: Float = 3.1415927f
    const val NVG_CCW: Int = 1
    const val NVG_CW: Int = 2
    const val NVG_SOLID: Int = 1
    const val NVG_HOLE: Int = 2
    const val NVG_BUTT: Int = 0
    const val NVG_ROUND: Int = 1
    const val NVG_SQUARE: Int = 2
    const val NVG_BEVEL: Int = 3
    const val NVG_MITER: Int = 4
    const val NVG_ALIGN_LEFT: Int = 1
    const val NVG_ALIGN_CENTER: Int = 2
    const val NVG_ALIGN_RIGHT: Int = 4
    const val NVG_ALIGN_TOP: Int = 8
    const val NVG_ALIGN_MIDDLE: Int = 16
    const val NVG_ALIGN_BOTTOM: Int = 32
    const val NVG_ALIGN_BASELINE: Int = 64
    const val NVG_ZERO: Int = 1
    const val NVG_ONE: Int = 2
    const val NVG_SRC_COLOR: Int = 4
    const val NVG_ONE_MINUS_SRC_COLOR: Int = 8
    const val NVG_DST_COLOR: Int = 16
    const val NVG_ONE_MINUS_DST_COLOR: Int = 32
    const val NVG_SRC_ALPHA: Int = 64
    const val NVG_ONE_MINUS_SRC_ALPHA: Int = 128
    const val NVG_DST_ALPHA: Int = 256
    const val NVG_ONE_MINUS_DST_ALPHA: Int = 512
    const val NVG_SRC_ALPHA_SATURATE: Int = 1024
    const val NVG_SOURCE_OVER: Int = 0
    const val NVG_SOURCE_IN: Int = 1
    const val NVG_SOURCE_OUT: Int = 2
    const val NVG_ATOP: Int = 3
    const val NVG_DESTINATION_OVER: Int = 4
    const val NVG_DESTINATION_IN: Int = 5
    const val NVG_DESTINATION_OUT: Int = 6
    const val NVG_DESTINATION_ATOP: Int = 7
    const val NVG_LIGHTER: Int = 8
    const val NVG_COPY: Int = 9
    const val NVG_XOR: Int = 10
    const val NVG_IMAGE_GENERATE_MIPMAPS: Int = 1
    const val NVG_IMAGE_REPEATX: Int = 2
    const val NVG_IMAGE_REPEATY: Int = 4
    const val NVG_IMAGE_FLIPY: Int = 8
    const val NVG_IMAGE_PREMULTIPLIED: Int = 16
    const val NVG_IMAGE_NEAREST: Int = 32

    const val NVG_ANTIALIAS: Int = 1
    const val NVG_STENCIL_STROKES: Int = 2
    const val NVG_DEBUG: Int = 4
    const val NVG_IMAGE_NODELETE: Int = 65536

    var cx by Delegates.notNull<Long>()
    private var glType by Delegates.notNull<GLType>()

    fun create(glType: GLType, flags: NVGFLags.() -> Int) {
        NVG.glType = glType
        cx = glType.create(flags(NVGFLags))
        logger.info { "Created NanoVG Context [$cx] for ${glType.name}}" }
    }

    object NVGFLags {
        const val antialias: nvgFlag = NVG_ANTIALIAS
        const val stencilStrokes: nvgFlag = NVG_STENCIL_STROKES
        const val debug: nvgFlag = NVG_DEBUG

        operator fun nvgFlag.plus(flags: nvgFlag): nvgFlag {
            return flags or this
        }
    }

    enum class GLType(val create: (Int) -> Long) {
        GL2({ flags -> NanoVGGL2.nvgCreate(flags) }),
        GL3({ flags -> NanoVGGL3.nvgCreate(flags) }),
        GLES2({ flags -> NanoVGGLES2.nvgCreate(flags) }),
        GLES3({ flags -> NanoVGGLES3.nvgCreate(flags) })
    }

    fun beginFrame(width: Float, height: Float, dpi: Float) = NanoVG.nvgBeginFrame(cx, width, height, dpi)
    fun resetTransformation() = NanoVG.nvgResetTransform(cx)
    fun cancelFrame() = NanoVG.nvgCancelFrame(cx)
    fun endFrame() = NanoVG.nvgEndFrame(cx)
    fun globalCompositeOperation(op: Int) = NanoVG.nvgGlobalCompositeOperation(cx, op)
    fun globalCompositeBlendFunc(src: Int, dst: Int) = NanoVG.nvgGlobalCompositeBlendFunc(cx, src, dst)
    fun globalCompositeBlendFuncSeparate(srcRGB: Int, dstRGB: Int, srcAlpha: Int, dstAlpha: Int) = NanoVG.nvgGlobalCompositeBlendFuncSeparate(
        cx, srcRGB, dstRGB, srcAlpha, dstAlpha)
    fun colorRGB(r: Byte, g: Byte, b: Byte, result: NVGColor) = NanoVG.nvgRGB(r, g, b, result)
    fun colorRGBf(r: Float, g: Float, b: Float, result: NVGColor) = NanoVG.nvgRGBf(r, g, b, result)
    fun colorRGBA(r: Byte, g: Byte, b: Byte, a: Byte, result: NVGColor) = NanoVG.nvgRGBA(r, g, b, a, result)
    fun colorRGBAf(r: Float, g: Float, b: Float, a: Float, result: NVGColor) = NanoVG.nvgRGBAf(r, g, b, a, result)

    fun lerpRGBA(c0: NVGColor, c1: NVGColor, u: Float, result: NVGColor): NVGColor {
        return NanoVG.nvgLerpRGBA(c0, c1, u, result)
    }

    fun transRGBA(c0: NVGColor, a: Byte, result: NVGColor): NVGColor {
        return NanoVG.nvgTransRGBA(c0, a, result)
    }

    fun transRGBAf(c0: NVGColor, a: Float, result: NVGColor): NVGColor {
        return NanoVG.nvgTransRGBAf(c0, a, result)
    }

    fun colorHSL(h: Float, s: Float, l: Float, result: NVGColor): NVGColor {
        return NanoVG.nvgHSL(h, s, l, result)
    }

    fun colorHSLA(h: Float, s: Float, l: Float, a: Byte, result: NVGColor): NVGColor {
        return NanoVG.nvgHSLA(h, s, l, a, result)
    }


    fun save() {
        NanoVG.nvgSave(cx)
    }


    fun restore() {
        NanoVG.nvgRestore(cx)
    }

    fun reset() {
        NanoVG.nvgReset(cx)
    }

    fun shapeAntiAlias(enabled: Boolean) {
        NanoVG.nvgShapeAntiAlias(cx, enabled)
    }


    fun strokeColor(color: NVGColor) {
        NanoVG.nvgStrokeColor(cx, color)
    }


    fun strokePaint(paint: NVGPaint) {
        NanoVG.nvgStrokePaint(cx, paint)
    }


    fun fillColor(color: NVGColor) {
        NanoVG.nvgFillColor(cx, color)
    }


    fun fillPaint(paint: NVGPaint) {
        NanoVG.nvgFillPaint(cx, paint)
    }

    fun miterLimit(limit: Float) {
        NanoVG.nvgMiterLimit(cx, limit)
    }


    fun strokeWidth(width: Float) {
        NanoVG.nvgStrokeWidth(cx, width)
    }

    fun lineCap(cap: Int) {
        NanoVG.nvgLineCap(cx, cap)
    }

    fun lineJoin(join: Int) {
        NanoVG.nvgLineJoin(cx, join)
    }

    var globalAlpha = 1f
        set(value) {
            field = value
            NanoVG.nvgGlobalAlpha(cx, field)
        }

    fun transform(a: Float, b: Float, c: Float, d: Float, e: Float, f: Float) {
        NanoVG.nvgTransform(cx, a, b, c, d, e, f)
    }


    fun translate(x: Float, y: Float) {
        NanoVG.nvgTranslate(cx, x, y)
    }


    fun rotate(angle: Float) {
        NanoVG.nvgRotate(cx, angle)
    }

    fun skewX(angle: Float) {
        NanoVG.nvgSkewX(cx, angle)
    }

    fun skewY(angle: Float) {
        NanoVG.nvgSkewY(cx, angle)
    }

    fun scale(x: Float, y: Float) {
        NanoVG.nvgScale(cx, x, y)
    }

    fun currentTransform(xFrom: FloatBuffer) {
        NanoVG.nvgCurrentTransform(cx, xFrom)
    }

    fun transformIdentity(dst: FloatBuffer) {
        NanoVG.nvgTransformIdentity(dst)
    }

    fun transformTranslate(dst: FloatBuffer, x: Float, y: Float) {
        NanoVG.nvgTransformTranslate(dst, x, y)
    }

    fun transformScale(dst: FloatBuffer, x: Float, y: Float) {
        NanoVG.nvgTransformScale(dst, x, y)
    }

    fun transformRotate(dst: FloatBuffer, angle: Float) {
        NanoVG.nvgTransformRotate(dst, angle)
    }

    fun transformSkewX(dst: FloatBuffer, angle: Float) {
        NanoVG.nvgTransformSkewX(dst, angle)
    }

    fun transformSkewY(dst: FloatBuffer, angle: Float) {
        NanoVG.nvgTransformSkewY(dst, angle)
    }

    fun transformMultiply(dst: FloatBuffer, src: FloatBuffer) {
        NanoVG.nvgTransformMultiply(dst, src)
    }

    fun transfromPremultiply(dst: FloatBuffer, src: FloatBuffer) {
        NanoVG.nvgTransformPremultiply(dst, src)
    }

    fun transfromInverse(dst: FloatBuffer, src: FloatBuffer) {
        NanoVG.nvgTransformInverse(dst, src)
    }

    fun transfromPoint(dstx: FloatBuffer, dsty: FloatBuffer, xfrom: FloatBuffer, srcX: Float, srcY: Float) {
        NanoVG.nvgTransformPoint(dstx, dsty, xfrom, srcX, srcY)
    }

    fun toRadians(x: Float): Float {
        return NanoVG.nvgDegToRad(x)
    }

    fun toDegrees(x: Float): Float {
        return NanoVG.nvgDegToRad(x)
    }

    fun createImage(filename: CharSequence, imageFlags: Int): Int {
        return NanoVG.nvgCreateImage(cx, filename, imageFlags)
    }

    fun createImageMem(imageFlags: Int, data: ByteBuffer): Int {
        return NanoVG.nvgCreateImageMem(cx, imageFlags, data)
    }

    fun createImageRGBA(w: Int, h: Int, imageFlags: Int, data: ByteBuffer): Int {
        return NanoVG.nvgCreateImageRGBA(cx, w, h, imageFlags, data)
    }

    fun createImageFromHandle(handle: Int, w: Int, h: Int, imageFlags: Int): Int {
        return when (glType) {
            GLType.GL2 -> NanoVGGL2.nvglCreateImageFromHandle(cx, handle, w, h, imageFlags)
            GLType.GL3 -> NanoVGGL3.nvglCreateImageFromHandle(cx, handle, w, h, imageFlags)
            GLType.GLES2 -> NanoVGGLES2.nvglCreateImageFromHandle(cx, handle, w, h, imageFlags)
            GLType.GLES3 -> NanoVGGLES3.nvglCreateImageFromHandle(cx, handle, w, h, imageFlags)
        }
    }

    fun imageHandle(image: Int): Int {
        return when (glType) {
            GLType.GL2 -> NanoVGGL2.nvglImageHandle(cx, image)
            GLType.GL3 -> NanoVGGL3.nvglImageHandle(cx, image)
            GLType.GLES2 -> NanoVGGLES2.nvglImageHandle(cx, image)
            GLType.GLES3 -> NanoVGGLES3.nvglImageHandle(cx, image)
        }
    }

    fun createFrameBuffer(w: Int, h: Int, flags: Int): NVGLUFramebuffer {
        return when (glType) {
            GLType.GL2 -> NanoVGGL2.nvgluCreateFramebuffer(cx, w, h, flags)!!
            GLType.GL3 -> NanoVGGL3.nvgluCreateFramebuffer(cx, w, h, flags)!!
            GLType.GLES2 -> NanoVGGLES2.nvgluCreateFramebuffer(cx, w, h, flags)!!
            GLType.GLES3 -> NanoVGGLES3.nvgluCreateFramebuffer(cx, w, h, flags)!!
        }
    }

    fun setFrameBuffer(fb: NVGLUFramebuffer?) {
        when (glType) {
            GLType.GL2 -> NanoVGGL2.nvgluBindFramebuffer(cx, fb)
            GLType.GL3 -> NanoVGGL3.nvgluBindFramebuffer(cx, fb)
            GLType.GLES2 -> NanoVGGLES2.nvgluBindFramebuffer(cx, fb)
            GLType.GLES3 -> NanoVGGLES3.nvgluBindFramebuffer(cx, fb)
        }
    }

    fun updateImage(image: Int, data: ByteBuffer) {
        NanoVG.nvgUpdateImage(cx, image, data)
    }

    fun imageSize(image: Int, w: IntBuffer, h: IntBuffer) {
        NanoVG.nvgImageSize(cx, image, w, h)
    }

    fun imageSize(image: Int, w: IntArray, h: IntArray) {
        NanoVG.nvgImageSize(cx, image, w, h)
    }

    fun deleteImage(image: Int) {
        NanoVG.nvgDeleteImage(cx, image)
    }


    fun linearGradient(
        sx: Float,
        sy: Float,
        ex: Float,
        ey: Float,
        from: NVGColor,
        to: NVGColor,
        result: NVGPaint,
    ): NVGPaint {
        return NanoVG.nvgLinearGradient(cx, sx, sy, ex, ey, from, to, result)
    }

    fun boxGradient(
        x: Float,
        y: Float,
        w: Float,
        h: Float,
        r: Float,
        f: Float,
        from: NVGColor,
        to: NVGColor,
        result: NVGPaint,
    ): NVGPaint {
        return NanoVG.nvgBoxGradient(cx, x, y, w, h, r, f, from, to, result)
    }


    fun imagePattern(
        ox: Float,
        oy: Float,
        ex: Float,
        ey: Float,
        angle: Float,
        image: Int,
        alpha: Float,
        result: NVGPaint,
    ): NVGPaint {
        return NanoVG.nvgImagePattern(cx, ox, oy, ex, ey, angle, image, alpha, result)
    }

    fun scissor(x: Float, y: Float, w: Float, h: Float) {
        NanoVG.nvgScissor(cx, x, y, w, h)
    }

    fun intersectScissor(x: Float, y: Float, w: Float, h: Float) {
        NanoVG.nvgIntersectScissor(cx, x, y, w, h)
    }

    fun resetScissor() {
        NanoVG.nvgResetScissor(cx)
    }


    fun beginPath() {
        NanoVG.nvgBeginPath(cx)
    }

    fun moveTo(x: Float, y: Float) {
        NanoVG.nvgMoveTo(cx, x, y)
    }

    fun lineTo(x: Float, y: Float) {
        NanoVG.nvgLineTo(cx, x, y)
    }

    fun bezierTo(cx1: Float, cy1: Float, cx2: Float, cy2: Float, x: Float, y: Float) {
        NanoVG.nvgBezierTo(cx, cx1, cy1, cx2, cy2, x, y)
    }

    fun guadTo(cx: Float, cy: Float, x: Float, y: Float) {
        NanoVG.nvgQuadTo(NVG.cx, cx, cy, x, y)
    }

    fun arcTo(x1: Float, y1: Float, x2: Float, y2: Float, radius: Float) {
        NanoVG.nvgArcTo(cx, x1, y1, x2, y2, radius)
    }

    fun arc(x: Float, y: Float, r: Float, a0: Float, a1: Float, dir: Int) {
        NanoVG.nvgArc(cx, x, y, r, a0, a1, dir)
    }

    fun closePath() {
        NanoVG.nvgClosePath(cx)
    }

    fun pathWinding(op: Int) {
        NanoVG.nvgPathWinding(cx, op)
    }

    fun rect(x: Float, y: Float, w: Float, h: Float) {
        NanoVG.nvgRect(cx, x, y, w, h)
    }


    fun rrect(x: Float, y: Float, w: Float, h: Float, rad: Float) {
        NanoVG.nvgRoundedRect(cx, x, y, w, h, rad)
    }

    fun rrect(x: Float, y: Float, w: Float, h: Float, rTL: Float, rTR: Float, rBL: Float, rBR: Float) {
        NanoVG.nvgRoundedRectVarying(cx, x, y, w, h, rTL, rTR, rBL, rBR)
    }

    fun ellipse(cx: Float, cy: Float, rx: Float, ry: Float) {
        NanoVG.nvgEllipse(NVG.cx, cx, cy, rx, ry)
    }

    fun circle(x: Float, y: Float, r: Float) {
        NanoVG.nvgCircle(cx, x, y, r)
    }

    fun fill() {
        NanoVG.nvgFill(cx)
    }

    fun stroke() {
        NanoVG.nvgStroke(cx)
    }

    fun createFont(name: CharSequence, filename: CharSequence): Int {
        return NanoVG.nvgCreateFont(cx, name, filename)
    }

    fun createFontAtIndex(name: CharSequence, filename: CharSequence, index: Int): Int {
        return NanoVG.nvgCreateFontAtIndex(cx, name, filename, index)
    }

    fun createFontMem(name: CharSequence, data: ByteBuffer, freeData: Boolean): Int {
        val id = NanoVG.nvgCreateFontMem(cx, name, data, freeData)
        return id
    }

    fun createFontMemAtIndex(name: CharSequence, data: ByteBuffer, freeData: Boolean, index: Int): Int {
        return NanoVG.nvgCreateFontMemAtIndex(cx, name, data, freeData, index)
    }

    fun findFont(name: CharSequence): Int {
        return NanoVG.nvgFindFont(cx, name)
    }

    fun addFallbackFontID(baseFont: Int, fallbackFont: Int): Int {
        return NanoVG.nvgAddFallbackFontId(cx, baseFont, fallbackFont)
    }

    fun addFallbackFont(baseFont: CharSequence, fallbackFont: CharSequence): Int {
        return NanoVG.nvgAddFallbackFont(cx, baseFont, fallbackFont)
    }

    fun resetFallbackFontsID(baseFont: Int) {
        NanoVG.nvgResetFallbackFontsId(cx, baseFont)
    }

    fun resetFallbackFonts(baseFont: CharSequence) {
        NanoVG.nvgResetFallbackFonts(cx, baseFont)
    }


    fun fontSize(size: Float) {
        NanoVG.nvgFontSize(cx, size)
    }


    fun fontBlur(blur: Float) {
        NanoVG.nvgFontBlur(cx, blur)
    }

    fun textLetterSpacing(spacing: Float) {
        NanoVG.nvgTextLetterSpacing(cx, spacing)
    }

    fun textLineHeight(lineHeight: Float) {
        NanoVG.nvgTextLineHeight(cx, lineHeight)
    }


    fun textAlign(align: Int) {
        NanoVG.nvgTextAlign(cx, align)
    }

    fun fontFaceID(font: Int) {
        NanoVG.nvgFontFaceId(cx, font)
    }


    fun fontFace(font: CharSequence) {
        NanoVG.nvgFontFace(cx, font)
    }


    fun text(text: CharSequence, x: Float, y: Float) {
        NanoVG.nvgText(cx, x, y, text)
    }

    fun textBox(x: Float, y: Float, breakLine: Float, text: CharSequence) {
        NanoVG.nvgTextBox(cx, x, y, breakLine, text)
    }

    fun textBounds(x: Float, y: Float, text: CharSequence, bounds: FloatBuffer): Float {
        return NanoVG.nvgTextBounds(cx, x, y, text, bounds)
    }

    fun textBounds(x: Float, y: Float, text: CharSequence, bounds: FloatArray): Float {
        return NanoVG.nvgTextBounds(cx, x, y, text, bounds)
    }

    fun textBoxBounds(x: Float, y: Float, breakRowWidth: Float, text: CharSequence, bounds: FloatBuffer) {
        NanoVG.nvgTextBoxBounds(cx, x, y, breakRowWidth, text, bounds)
    }

    fun textGlyphPositions(x: Float, y: Float, text: CharSequence, positions: NVGGlyphPosition.Buffer): Int {
        return NanoVG.nvgTextGlyphPositions(cx, x, y, text, positions)
    }

    fun textMetrics(ascend: FloatBuffer, descend: FloatBuffer, lineh: FloatBuffer) {
        NanoVG.nvgTextMetrics(cx, ascend, descend, lineh)
    }

    fun textBreakLines(text: CharSequence, breakRowWidth: Float, rows: NVGTextRow.Buffer): Int {
        return NanoVG.nvgTextBreakLines(cx, text, breakRowWidth, rows)
    }
}
