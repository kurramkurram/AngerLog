package io.github.kurramkurram.angerlog.ui.component.calendarpicker

import java.time.YearMonth

class CalendarPickerUiState(
    val now: YearMonth = YearMonth.now(),
    val yearDropDown: Boolean = false,
    val monthDropDown: Boolean = false,
    val yearMonth: YearMonth = YearMonth.now(),
    val daysInMonth: Int = yearMonth.lengthOfMonth(),
    val firstDayOfWeek: Int = yearMonth.atDay(1).dayOfWeek.value,
) {
    fun minusMonths(): CalendarPickerUiState = CalendarPickerUiState(now, yearDropDown, monthDropDown, yearMonth.minusMonths(1))

    fun plusMonths(): CalendarPickerUiState = CalendarPickerUiState(now, yearDropDown, monthDropDown, yearMonth.plusMonths(1))

    fun selectYear(year: Int): CalendarPickerUiState = CalendarPickerUiState(now, yearDropDown, monthDropDown, yearMonth.withYear(year))

    fun selectMonth(month: Int): CalendarPickerUiState = CalendarPickerUiState(now, yearDropDown, monthDropDown, yearMonth.withMonth(month))

    fun showYearDropDown(): CalendarPickerUiState = CalendarPickerUiState(now, true, monthDropDown, yearMonth)

    fun showMonthDropDown(): CalendarPickerUiState = CalendarPickerUiState(now, yearDropDown, true, yearMonth)

    fun closeYearDropDown(): CalendarPickerUiState = CalendarPickerUiState(now, false, monthDropDown, yearMonth)

    fun closeMonthDropDown(): CalendarPickerUiState = CalendarPickerUiState(now, yearDropDown, false, yearMonth)
}
