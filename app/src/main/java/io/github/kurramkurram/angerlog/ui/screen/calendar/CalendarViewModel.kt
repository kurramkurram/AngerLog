package io.github.kurramkurram.angerlog.ui.screen.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.kurramkurram.angerlog.ui.component.calendarpicker.CalendarPickerUiState
import io.github.kurramkurram.angerlog.util.L
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.YearMonth

private const val MAX_LOADING_TIME = 500

class CalendarViewModel(
    private val calendarDataUseCase: CalendarDataUseCase,
    yearMonth: YearMonth = YearMonth.now(),
) : ViewModel() {
    private val _state: MutableStateFlow<CalendarUiState> =
        MutableStateFlow(CalendarUiState.Loading)
    val state = _state.asStateFlow()

    private val _yearMonthState: MutableStateFlow<CalendarPickerUiState> =
        MutableStateFlow(CalendarPickerUiState(now = yearMonth))
    val yearMonthState = _yearMonthState.asStateFlow()

    private lateinit var job: Job

    init {
        updateAngerLogByMonth()
    }

    fun minusMonths() = _yearMonthState.update { it.minusMonths() }

    fun plusMonths() = _yearMonthState.update { it.plusMonths() }

    fun selectYear(year: Int) = _yearMonthState.update { it.selectYear(year) }

    fun selectMonth(month: Int) = _yearMonthState.update { it.selectMonth(month) }

    fun showYearDropDown() = _yearMonthState.update { it.showYearDropDown() }

    fun showMonthDropDown() = _yearMonthState.update { it.showMonthDropDown() }

    fun closeYearDropDown() = _yearMonthState.update { it.closeYearDropDown() }

    fun closeMonthDropDown() = _yearMonthState.update { it.closeMonthDropDown() }

    fun canShowNextArrow(): Boolean = _yearMonthState.value.yearMonth < _yearMonthState.value.now

    fun selectDay(day: Int) {
        val success = _state.value as CalendarUiState.Success
        val ids = success.calendarItemList[day - 1]
        if (ids == null) {
            _state.update { (it as CalendarUiState.Success).copy(selectDay = day) }
        } else {
            _state.update {
                (it as CalendarUiState.Success).copy(
                    showDialog = true,
                    selectDay = day,
                )
            }
        }
    }

    fun clearDay() {
        _state.update {
            (it as CalendarUiState.Success).copy(
                showDialog = false,
                selectDay = 0,
            )
        }
    }

    fun updateAngerLogByMonth() {
        val yearMonth = _yearMonthState.value.yearMonth
        if (::job.isInitialized) job.cancel()
        job = viewModelScope.launch {
            _state.value = CalendarUiState.Loading
            val start = System.currentTimeMillis()
            calendarDataUseCase.execute(yearMonth).map {
                it
            }.catch { CalendarUiState.Error }.collect {
                val end = System.currentTimeMillis()
                val diff = end - start
                if (MAX_LOADING_TIME > diff) {
                    delay(MAX_LOADING_TIME - diff)
                }
                _state.value = it
            }
        }
    }
}
