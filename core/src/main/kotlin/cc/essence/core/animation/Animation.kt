package cc.essence.core.animation

typealias AnimationListener = () -> Unit

class Animation(
    val duration: Long,
    private val easingFunction: Easing = Easing.LINEAR,
    shouldStart: Boolean = false,
    private val reverse: Boolean = false,
) {
    private var startTime: Long = 0L
    private var endTime: Long = 0L
    private var isPaused: Boolean = false
    private var pauseTime: Long = 0L
    private var started = false

    private val onCompleteListeners = mutableListOf<AnimationListener>()
    private val startWithListeners = mutableListOf<AnimationListener>()

    init {
        if (shouldStart) {
            start()
        }
    }

    fun start() {
        if (started) return
        startTime = System.nanoTime()
        endTime = startTime + duration * 1_000_000
        isPaused = false
        started = true
        startWithListeners.forEach { it() }
    }

    fun stop() {
        startTime = 0L
        endTime = 0L
        isPaused = false
        started = false
    }

    fun pause() {
        if (!isPaused) {
            pauseTime = System.nanoTime()
            isPaused = true
        }
    }

    fun resume() {
        if (isPaused) {
            val pauseDuration = System.nanoTime() - pauseTime
            startTime += pauseDuration
            endTime += pauseDuration
            isPaused = false
        }
    }

    fun startAfter(previousAnimation: Animation) = apply {
        previousAnimation.onCompleteListeners.add {
            start()
        }
    }

    fun startWith(otherAnimation: Animation) = apply {
        otherAnimation.startWithListeners.add {
            start()
        }
    }

    fun completed(): Boolean = System.nanoTime() > endTime

    fun value(): Double {
        if (completed()) {
            onCompleteListeners.forEach { it() }
            return if (reverse) 0.0 else 1.0
        }
        if (isPaused) return (pauseTime - startTime).toDouble() / (endTime - startTime)
        val currentTime = System.nanoTime()
        val progress = (currentTime - startTime).toDouble() / (endTime - startTime)
        val adjustedProgress = if (reverse) 1.0 - progress else progress
        return easingFunction.f(adjustedProgress.coerceIn(0.0, 1.0))
    }
}