package io.github.kurramkurram.angerlog.model

import android.annotation.SuppressLint

/**
 * 時間・分を扱う.
 *
 * @param hour 時間
 * @param minute 分
 */
data class Time(val hour: Int, val minute: Int) {
    /**
     * 時間・分を00:00形式の文字列にする.
     */
    @SuppressLint("DefaultLocale")
    val time: String = String.format("%02d:%02d", hour, minute)
}
