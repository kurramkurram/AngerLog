package io.github.kurramkurram.angerlog.ui.screen.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.kurramkurram.angerlog.ui.component.calendarpicker.CalendarPickerUiState
import io.github.kurramkurram.angerlog.ui.component.calendarpicker.DEFAULT_MIN_YEAR_OF_PICKER
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.YearMonth

/**
 * データ取得のタイムアウト時間.
 */
private const val MAX_LOADING_TIME = 500

/**
 * カレンダー画面の状態.
 *
 * @param calendarDataUseCase カレンダー画面で表示するデータを生成する
 * @param yearMonth 分析画面で表示したい年月
 */
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

    /**
     * 月をひと月戻す.
     */
    fun minusMonths() = _yearMonthState.update { it.minusMonths() }

    /**
     * 月をひと月進める.
     */
    fun plusMonths() = _yearMonthState.update { it.plusMonths() }

    /**
     * 年を選択した.
     *
     * @param year 選択した年
     */
    fun selectedYear(year: Int) = _yearMonthState.update { it.changeYear(year) }

    /**
     * 月を選択した.
     *
     * @param month 選択した月
     */
    fun selectedMonth(month: Int) = _yearMonthState.update { it.changeMonth(month) }

    /**
     * 「年」のドロップダウンを表示する.
     */
    fun showYearDropDown() = _yearMonthState.update { it.showYearDropDown() }

    /**
     * 「月」のドロップダウンを表示する.
     */
    fun showMonthDropDown() = _yearMonthState.update { it.showMonthDropDown() }

    /**
     * 「年」のドロップダウンを閉じる.
     */
    fun closeYearDropDown() = _yearMonthState.update { it.closeYearDropDown() }

    /**
     * 「月」のドロップダウンを閉じる.
     */
    fun closeMonthDropDown() = _yearMonthState.update { it.closeMonthDropDown() }

    /**
     * 「＜」の表示可否を判定する.
     * [DEFAULT_MIN_YEAR_OF_PICKER]の1月より古い年月は表示しない.
     *
     * @return true: 表示する
     */
    fun canShowBackArrow(): Boolean =
        _yearMonthState.value.yearMonth > YearMonth.of(DEFAULT_MIN_YEAR_OF_PICKER, 1)

    /**
     * 「＞」の表示可否を判定する.
     * 現在の年月より新しい年月には移動しないため、現在の年月では表示しない.
     *
     * @return true: 表示する
     */
    fun canShowNextArrow(): Boolean = _yearMonthState.value.yearMonth < _yearMonthState.value.now

    /**
     * 日付を選択する.
     *
     * @param day 日にち
     */
    fun selectDay(day: Int) {
        val s = _state.value
        if (s is CalendarUiState.Success) {
            val ids = s.calendarItemList[day - 1]
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
    }

    /**
     * 選択中の日にちをクリアする.
     */
    fun clearDay() {
        if (_state.value is CalendarUiState.Success) {
            _state.update {
                (it as CalendarUiState.Success).copy(
                    showDialog = false,
                    selectDay = 0,
                )
            }
        }
    }

    /**
     * カレンダー画面に表示する情報を取得し、反映させる.
     */
    fun updateAngerLogByMonth() {
        val yearMonth = _yearMonthState.value.yearMonth
        if (::job.isInitialized) {
            job.cancel()
        }
        job =
            viewModelScope.launch {
                _state.value = CalendarUiState.Loading
                val start = System.currentTimeMillis()
                calendarDataUseCase.execute(yearMonth).map {
                    it
                }.catch { _state.value = CalendarUiState.Error }.collect {
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
