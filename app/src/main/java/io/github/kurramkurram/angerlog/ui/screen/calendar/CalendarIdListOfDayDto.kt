package io.github.kurramkurram.angerlog.ui.screen.calendar

/**
 * カレンダー表示に利用するデータ.
 *
 * @param day 日にち
 * @param ids データベースの一意のidと怒りの強さの組み合わせ
 */
data class CalendarIdListOfDayDto(
    val day: Int,
    val ids: List<Pair<Long, Int>>,
)
