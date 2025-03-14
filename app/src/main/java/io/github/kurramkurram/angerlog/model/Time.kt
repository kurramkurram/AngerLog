package io.github.kurramkurram.angerlog.model

import android.annotation.SuppressLint

data class Time(val hour: Int, val minute: Int) {
    @SuppressLint("DefaultLocale")
    val time: String = String.format("%02d:%02d", hour, minute)
}
