package io.github.kurramkurram.angerlog.model

private const val DAY_OF_TIME = 24 * 60 * 60 * 1000
private const val LOOK_BACK_DAY = 3

class ShowLookBack(now: Long, logDate: Long) {
    val showLookBack: Boolean

    init {
        val elapsedTime = now - logDate
        showLookBack = elapsedTime / DAY_OF_TIME > LOOK_BACK_DAY
    }
}