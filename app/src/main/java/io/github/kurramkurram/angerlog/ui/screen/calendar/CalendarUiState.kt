package io.github.kurramkurram.angerlog.ui.screen.calendar

import java.util.Calendar

sealed class CalendarUiState {
    data object Loading : CalendarUiState()

    data class Success(
        val calendarItemList: List<AngerIdListOfDayDto?> = emptyList(),
        val today: Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
        val showDialog: Boolean = false,
        val selectDay: Int = 0,
    ) : CalendarUiState()

    data object Error : CalendarUiState()
}
