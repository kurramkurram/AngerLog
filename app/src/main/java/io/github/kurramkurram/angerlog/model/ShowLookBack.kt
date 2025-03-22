package io.github.kurramkurram.angerlog.model

/**
 * 1日のミリ秒.
 */
private const val DAY_OF_TIME = 24 * 60 * 60 * 1000

/**
 * 振り返りを行えるようになるための期間.
 */
private const val LOOK_BACK_DAY = 3

/**
 * 振り返りを行うかどうかを扱う.
 *
 * @param now 現在時刻
 * @param logDate 怒りを記録した日時
 */
class ShowLookBack(now: Long, logDate: Long) {
    /**
     * 振り返りを行うかどうかの判定値.
     * trueの時に振り返りを行うことができる.
     */
    val showLookBack: Boolean

    init {
        val elapsedTime = now - logDate
        showLookBack = elapsedTime / DAY_OF_TIME > LOOK_BACK_DAY
    }
}
