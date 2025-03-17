package io.github.kurramkurram.angerlog.ui.screen.home

import io.github.kurramkurram.angerlog.model.AngerLog
import io.github.kurramkurram.angerlog.model.ShowLookBack
import io.github.kurramkurram.angerlog.util.DateConverter.Companion.dateToString

class HomeAngerLogDto(
    val angerLog: AngerLog,
    now: Long,
) {
    private val showLookBackIcon = ShowLookBack(logDate = angerLog.date.time, now = now)

    fun getId(): Long = angerLog.id

    fun getDate(): String = angerLog.date.dateToString()

    fun getEvent(): String = angerLog.event

    fun getLevel(): Int = angerLog.level

    fun canShowLookBack(): Boolean = showLookBackIcon.showLookBack
}
