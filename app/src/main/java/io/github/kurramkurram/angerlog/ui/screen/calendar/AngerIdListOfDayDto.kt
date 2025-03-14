package io.github.kurramkurram.angerlog.ui.screen.calendar

data class AngerIdListOfDayDto(
    val day: Int,
    val ids: List<Pair<Long, Int>>,
)
