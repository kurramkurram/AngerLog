package io.github.kurramkurram.angerlog.ui.screen.analysis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.kurramkurram.angerlog.ui.component.calendarpicker.CalendarPickerUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.YearMonth

private const val MAX_LOADING_TIME = 500

class AnalysisViewModel(
    private val analysisDataUseCase: AnalysisDataUseCase,
    yearMonth: YearMonth = YearMonth.now(),
) : ViewModel() {
    private val _content: MutableStateFlow<AnalysisUiState> =
        MutableStateFlow(AnalysisUiState.Loading)
    val content = _content.asStateFlow()

    private val _yearMonthState: MutableStateFlow<CalendarPickerUiState> =
        MutableStateFlow(CalendarPickerUiState(now = yearMonth))
    val yearMonthState = _yearMonthState.asStateFlow()

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

    fun updateAngerLogByMonth() {
        val yearMonth = _yearMonthState.value.yearMonth
        viewModelScope.launch {
            _content.value = AnalysisUiState.Loading
            val start = System.currentTimeMillis()
            analysisDataUseCase.execute(yearMonth).map {
                it
            }.catch { AnalysisUiState.Error }.collect {
                val end = System.currentTimeMillis()
                val diff = end - start
                if (MAX_LOADING_TIME > diff) {
                    delay(MAX_LOADING_TIME - diff)
                }
                _content.value = it
            }
        }
    }
}
