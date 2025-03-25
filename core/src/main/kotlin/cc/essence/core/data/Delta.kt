package cc.essence.core.data

object Delta {
    var time: Float = 0f
        private set

    fun tick(fps: Int) {
        val delta = 1f / fps

        if (time.isNaN()) time = delta

        time += (delta - time) * 0.1f
    }
}