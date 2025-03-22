package io.github.kurramkurram.angerlog.ui.screen.calendar

/**
 * 日別のカレンダーデータ.
 *
 * @param day 日にち
 * @param id データベースの一意のid
 * @param level 怒りの強さ
 */
data class CalendarItemOfDayDto(
    val day: Int,
    val id: Long,
    val level: Int,
)
