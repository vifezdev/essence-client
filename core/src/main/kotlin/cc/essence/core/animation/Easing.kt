package cc.essence.core.animation

import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

enum class Easing(var f: (Double) -> Double) {
    LINEAR( { `in`: Double -> `in` }),
    SINE_IN( { `in`: Double -> (1f - cos((`in` * Math.PI) / 2)).toDouble() }),
    SINE_OUT( { `in`: Double ->
        sin((`in` * Math.PI) / 2)
            .toDouble()
    }),
    SINE_IN_OUT( { `in`: Double -> (-(cos(Math.PI * `in`) - 1) / 2).toDouble() }),
    QUAD_IN( { `in`: Double -> `in`.pow(2.0) as Double }),
    QUAD_OUT( { `in`: Double -> (1 - (1 - `in`).pow(2.0)).toDouble() }),
    QUAD_IN_OUT( { `in`: Double -> (if (`in` < 0.5) 2 * `in`.pow(2.0) else 1 - (-2 * `in` + 2).pow(2.0) / 2).toDouble() }),
    CUBIC_IN( { `in`: Double -> `in`.pow(3.0) as Double }),
    CUBIC_OUT( { `in`: Double -> (1 - (1 - `in`).pow(3.0)).toDouble() }),
    CUBIC_IN_OUT( { `in`: Double -> (if (`in` < 0.5) 4 * `in`.pow(3.0) else 1 - (-2 * `in` + 2).pow(3.0) / 2).toDouble() }),
    QUART_IN( { `in`: Double -> `in`.pow(4.0) as Double }),
    QUART_OUT( { `in`: Double -> (1 - (1 - `in`).pow(4.0)).toDouble() }),
    QUART_IN_OUT( { `in`: Double -> (if (`in` < 0.5f) 8 * `in`.pow(4.0) else 1 - (-2 * `in` + 2).pow(4.0) / 2).toDouble() }),
    QUINT_IN( { `in`: Double -> `in`.pow(5.0) as Double }),
    QUINT_OUT( { `in`: Double -> (1 - (1 - `in`).pow(5.0)).toDouble() }),
    QUINT_IN_OUT( { `in`: Double -> (if (`in` < 0.5) 16 * `in`.pow(5.0) else 1 - (-2 * `in` + 2).pow(5.0) / 2).toDouble() }),
    EXPO_IN( { `in`: Double -> 2.0.pow((10 * `in` - 10)) }),
    EXPO_OUT( { `in`: Double -> (1 - 2.0.pow((10 * `in` - 10))) }),
    EXPO_IN_OUT( { `in`: Double ->
        (if (`in` < 0.5f) 2.0.pow((20 * `in` - 10)) / 2 else (2 - 2.0.pow(
            (-20 * `in` + 10)
        )) / 2).toDouble()
    }),
    CIRC_IN( { `in`: Double -> (1 - sqrt(1 - `in`.pow(2.0))).toDouble() }),
    CIRC_OUT( { `in`: Double ->
        sqrt(1 - (`in` - 1).pow(2.0))
            .toDouble()
    }),
    CIRC_IN_OUT( { `in`: Double ->
        (if (`in` < 0.5) (1 - sqrt(1 - (2 * `in`).pow(2.0))) / 2 else (sqrt(
            1 - (-2 * `in` + 2).pow(
                2.0
            )
        ) + 1) / 2).toDouble()
    }),
    BACK_IN( { `in`: Double -> (2.70158 * `in`.pow(3.0) - 1.70158 * `in`.pow(2.0)).toDouble() }),
    BACK_OUT( { `in`: Double -> (2.70158 * (`in` - 1).pow(3.0) - 1.70158 * (`in` - 1).pow(2.0)).toDouble() }),
    BACK_IN_OUT( { `in`: Double ->
        (if (`in` < 0.5f) ((2 * `in`).pow(2.0) * ((2.5949095 + 1) * 2 * `in` - 2.5949095)) / 2 else ((2 * `in` - 2).pow(
            2.0
        ) * ((2.5949095 + 1) * (`in` * 2 - 2) + 2.5949095) + 2) / 2).toDouble()
    }),
    ELASTIC_IN( { `in`: Double -> ((-2.0).pow((10 * `in` - 10)) * sin(`in` * 10 - 10.75) * (Math.PI * 2) / 3).toDouble() }),
    ELASTIC_OUT( { `in`: Double -> (2.0.pow((-10 * `in`)) * sin((`in` * 10 - 0)) * (Math.PI * 2) / 3).toDouble() + 1 }),
    ELASTIC_IN_OUT( { `in`: Double ->
        (if (`in` < 0.5f) -(2.0.pow((20 * `in` - 10)) * sin(
            (20 * `in` - 11.125f) * (Math.PI * 2) / 4.5f
        )) / 2 else (2.0.pow((-20 * `in` + 10)) * sin(
            (20 * `in` - 11.125f) * (Math.PI * 2) / 4.5f
        )) / 2 + 1f).toDouble()
    }),
    BOUNCE_OUT( { `in`: Double ->
       bounceOut(`in`)
    }),
    BOUNCE_IN( { `in`: Double -> (1 -bounceOut((1f - `in`))) }),
    BOUNCE_IN_OUT( { `in`: Double ->
        (if (`in` < 0.5f) (1 -bounceOut((1f - `in` * 2)) / 2) else (1f +bounceOut(
            (`in` * 2 + 1f)
        ) / 2))
    });
}

fun bounceOut(`in`: Double): Double {
    var i = `in`
    val n1 = 7.5625
    val d1 = 2.75

    return if (i < 1 / d1) {
        n1 * i * i
    } else if (i < 2 / d1) {
        n1 * ((1.5 / d1).let { i -= it; i }) * i + 0.75
    } else if (i < 2.5 / d1) {
        n1 * ((2.25 / d1).let { i -= it; i }) * i + 0.9375
    } else {
        n1 * ((2.625 / d1).let { i -= it; i }) * i + 0.984375
    }
}
