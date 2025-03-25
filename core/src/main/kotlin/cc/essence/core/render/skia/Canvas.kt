package cc.essence.core.render.skia

import cc.essence.bridge.BridgeRegistry
import cc.essence.core.data.interpolate
import cc.essence.core.data.toBounds
import cc.essence.core.data.toComponents
import cc.essence.core.data.Bounds as B
import cc.essence.core.data.vec4f
import cc.essence.core.resource.font.Font
import cc.essence.core.resource.font.objects.Poppins
import cc.essence.core.resource.image.Image
import cc.essence.core.util.Invoker
import io.github.humbleui.skija.*
import io.github.humbleui.types.RRect
import io.github.humbleui.types.Rect
import java.nio.ByteBuffer
import kotlin.math.*

object Canvas {
    lateinit var skia: io.github.humbleui.skija.Canvas

    object SkSl {
        val LINES = RuntimeEffect.makeForShader("""
                layout(color) uniform vec4 c1;
                layout(color) uniform vec4 c2;
                
                uniform float time;
                uniform float lineWidth;
                
                vec4 main(vec2 coord) {
                    float m = sin((coord.x / 3.0 - coord.y / 6.0) / lineWidth + time * 10.0) * 2.0;
                    return mix(c1, c2, smoothstep(0.0, 1.0, step(0.5, m)));
                }
            """.trimIndent())!!

        fun lineEffect(x: Float, y: Float, w: Float, h: Float, r: Float, c1: Number, c2: Number, lw: Float, time: Float = 0f) {
            val cc1 = c1.toComponents(4)
            val cc2 = c2.toComponents(4)

            drawShader(x,y,w,h,r, LINES, UniformBuffer.build {
                vec4(cc1[1], cc1[2], cc1[3],cc1[0])
                vec4(cc2[1], cc2[2], cc2[3],cc2[0])
                float(time)
                float(lw)
            })
        }
    }

    fun paint(color: Number): Paint {
        return Paint().setColor(color.toInt())
    }

    fun Paint.stroke(width: Float): Paint {
        return this.setMode(PaintMode.STROKE).setStrokeWidth(width)
    }

    fun Paint.stroke(width: Float, color: Number): Paint {
        return this.setMode(PaintMode.STROKE_AND_FILL).setStrokeWidth(width).setColor(color.toInt())
    }

    fun frame(func: Invoker<Canvas>) {
        SkFrame.beginFrame()
        func.invoke(this)
        SkFrame.endFrame()
        SkFrame.render()
    }

    fun rect(x: Float, y: Float, w: Float, h: Float, paint: Paint) {
        skia.drawRect(Rect.makeXYWH(x, y, w, h), paint)
    }

    fun rect(bounds: B, paint: Paint) {
        rect(bounds.x, bounds.y, bounds.width, bounds.height, paint)
    }

    fun round(x: Float, y: Float, w: Float, h: Float, r: Float, paint: Paint) {
        skia.drawRRect(RRect.makeXYWH(x, y, w, h, r), paint)
    }

    fun round(bounds: B, r: Float, paint: Paint) {
        round(bounds.x, bounds.y, bounds.width, bounds.height, r, paint)
    }

    fun circle(x: Float, y: Float, r: Float, paint: Paint) {
        skia.drawOval(Rect.makeXYWH(x - r, y - r, r * 2, r * 2), paint)
    }

    fun text(string: String, x: Float, y: Float, size: Float, color: Number, align: Alignment = Alignment.LEFT_TOP, font: Font = Poppins.regular) {
        text(string, x, y, size, paint(color), align, font)
    }

    fun text(string: String, x: Float, y: Float, size: Float, paint: Paint = paint(-1), align: Alignment = Alignment.LEFT_TOP, font: Font = Poppins.regular) {
        val skFont = font.bakeSkiaSize(size)
        val line = TextLine.make(string, skFont)
        val metrics = skFont.metrics

        val height = metrics.descent - metrics.ascent

        val xPos = when (align) {
            Alignment.LEFT_TOP, Alignment.LEFT_MIDDLE, Alignment.LEFT_BOTTOM -> x
            Alignment.CENTER_TOP, Alignment.CENTER_MIDDLE, Alignment.CENTER_BOTTOM -> x - line.width / 2f
            else -> x - line.width
        }

        val yPos = when (align) {
            Alignment.LEFT_TOP, Alignment.CENTER_TOP, Alignment.RIGHT_TOP -> y + height - metrics.descent
            Alignment.LEFT_MIDDLE, Alignment.CENTER_MIDDLE, Alignment.RIGHT_MIDDLE -> y + height / 2f - metrics.descent
            Alignment.LEFT_BOTTOM, Alignment.CENTER_BOTTOM, Alignment.RIGHT_BOTTOM -> y - metrics.descent
        }

        skia.drawTextLine(line, xPos, yPos, paint)
    }

    fun shadow(x: Float, y: Float, w: Float, h: Float, r: Float, spread: Float, blur: Float, color: Number) {
        skia.drawRectShadow(RRect.makeXYWH(x, y, w, h, r), 0f, 0f, blur, spread, color.toInt())
    }

    fun shadow(bounds: B, r: Float, spread: Float, blur: Float, color: Number) {
        shadow(bounds.x, bounds.y, bounds.width, bounds.height, r, spread, blur, color)
    }

    fun image(x: Float, y: Float, w: Float, h: Float, image: Image) {
        skia.drawImageRect(image.get(), Rect.makeXYWH(x, y, w, h))
    }

    fun pushAlpha(alpha: Float, func: () -> Unit) {
        skia.saveLayerAlpha(null, (alpha.coerceIn(0f, 1f) * 255).toInt())
        func.invoke()
        skia.restore()
    }

    fun translate(x: Float, y: Float) {
        skia.translate(x, y)
    }

    fun scale(x: Float, y: Float) {
        skia.scale(x, y)
    }

    fun rotate(angle: Float) {
        skia.rotate(angle)
    }

    fun save() {
        skia.save()
    }

    fun restore() {
        skia.restore()
    }

    const val MVT_DIR_LEFT = 0b001
    const val MVT_DIR_RIGHT = 0b010
    const val MVT_DIR_BOTH = 0b100

    fun movingTextOverlay(text: String, x: Float, y: Float, w: Float, h: Float, r: Float, a: Float, fs: Float = 46f, direction: Int = MVT_DIR_LEFT, speedMultiplier: Float = 1f) {
        val clr = 0x20FFFFFF

//         0b001 = left, 0b010 = right, 0b100 = both

        val tw = textBounds(text, fs, Poppins.black).width + 20f
        val th = fs + 5f

        val timesW = ceil(w / tw).toInt() + 3
        val timesH = (ceil(h / th) * 1.2f).toInt() + 1

        val speed = tw.let { if (it.isNaN()) 1f else it } / 100f * speedMultiplier

        val t = System.currentTimeMillis() % (1000 * speed).toInt() / (1000 * speed)

        val longText = " $text ".repeat(timesW)
        val longTW = textBounds(longText, fs, Poppins.black).width + 20f

        save()
        skia.clipRRect(RRect.makeXYWH(x, y, w, h, r))
        translate(x, y)
        rotate((-.2f * 180 / PI).toFloat())
        for (j in 0 until timesH) {
            val isEven = j % 2 == 0
            val dirMult = when (direction) {
                MVT_DIR_LEFT -> -1
                MVT_DIR_RIGHT -> 1
                MVT_DIR_BOTH -> if (isEven) 1 else -1
                else -> 0
            }
            text(longText, -longTW/(timesW) + (longTW/timesW * t) * dirMult, th * j, fs, paint(0.interpolate(clr, a)), font = Poppins.black)
        }
        restore()
    }

    fun calcImgSizeAndPos(image: Image, displayWidth: Int = BridgeRegistry.display().width, displayHeight: Int = BridgeRegistry.display().height): vec4f {
        val ratioH = displayHeight.toFloat() / image.height
        val ratioW = displayWidth.toFloat() / image.width
        val ratio = maxOf(ratioH, ratioW)

        val imgW = image.width * ratio
        val imgH = image.height * ratio

        val imgX = (displayWidth - imgW) / 2
        val imgY = (displayHeight - imgH) / 2

        return vec4f(imgX, imgY, imgW, imgH)
    }

    fun textBounds(string: String, size: Float, font: Font): B {
        return textRect(string, size, font).toBounds()
    }

    fun textRect(string: String, size: Float, font: Font): Rect {
        return font.bakeSkiaSize(size).measureText(string)
    }

    fun drawShader(effect: RuntimeEffect, uBB: ByteBuffer) {
        skia.drawPaint(Paint().setShader(effect.makeShader(Data.makeFromBytes(ByteArray(uBB.remaining()).also { uBB.get(it) }), null, null)))
    }

    fun drawShader(x: Float, y: Float, w: Float, h: Float, r: Float, effect: RuntimeEffect, uBB: ByteBuffer) {
        val paint = Paint().setShader(effect.makeShader(Data.makeFromBytes(ByteArray(uBB.remaining()).also { uBB.get(it) }), null, null))
        skia.drawRRect(RRect.makeXYWH(x, y, w, h, r), paint)
    }
}