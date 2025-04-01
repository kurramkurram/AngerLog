package io.github.kurramkurram.angerlog.ui.component.calendarpicker

import io.github.kurramkurram.angerlog.ui.CustomDayOfWeek
import java.time.YearMonth

/**
 * 年月・ダイアログの表示状態
 *
 * @param now 現在年月
 * @param yearDropDown 「年」のドロップダウンの表示状態 true: 表示中
 * @param monthDropDown 「月」のドロップダウンの表示状態 true: 表示中
 * @param yearMonth 現在表示している年月
 * @param daysInMonth 現在表示している年月の日数 1月 -> 31, 4月 -> 30
 */
class CalendarPickerUiState(
    val now: YearMonth = YearMonth.now(),
    val yearDropDown: Boolean = false,
    val monthDropDown: Boolean = false,
    val yearMonth: YearMonth = YearMonth.now(),
    val daysInMonth: Int = yearMonth.lengthOfMonth(),
) {
    /**
     * 現在表示している年月の初日の曜日.
     */
    val firstDayOfWeek: Int

    /**
     * ドロップダウンで選択できる年の最大.
     */
    val dropDownMaxYear: Int

    /**
     * ドロップダウンで選択できる月の最大.
     */
    val dropDownMaxMonth: Int

    init {
        val customDayOfWeek = CustomDayOfWeek()
        firstDayOfWeek = customDayOfWeek.select(yearMonth.atDay(1).dayOfWeek.value).getValue()

        // 現在の年まで選択可能とする
        dropDownMaxYear = now.year

        // 選択中の年と現在の年が同じ場合、現在の月まで選択可能とする
        dropDownMaxMonth =
            if (yearMonth.year == now.year) {
                now.month.value - 1
            } else {
                12
            }
    }

    /**
     * 月をひと月戻す.
     *
     * @return 更新した[CalendarPickerUiState]
     */
    fun minusMonths(): CalendarPickerUiState = CalendarPickerUiState(now, yearDropDown, monthDropDown, yearMonth.minusMonths(1))

    /**
     * 月をひと月進める.
     *
     * @return 更新した[CalendarPickerUiState]
     */
    fun plusMonths(): CalendarPickerUiState = CalendarPickerUiState(now, yearDropDown, monthDropDown, yearMonth.plusMonths(1))

    /**
     * 年を選択する.
     *
     * @param year 変更する年
     * @return 更新した[CalendarPickerUiState]
     */
    fun changeYear(year: Int): CalendarPickerUiState {
        val selectYm = YearMonth.of(year, yearMonth.month)
        val displayYm =
            if (selectYm > now) {
                now
            } else {
                yearMonth.withYear(year)
            }
        return CalendarPickerUiState(now, yearDropDown, monthDropDown, displayYm)
    }

    /**
     * 月を選択する.
     *
     * @param month 変更する月
     * @return 更新した[CalendarPickerUiState]
     */
    fun changeMonth(month: Int): CalendarPickerUiState = CalendarPickerUiState(now, yearDropDown, monthDropDown, yearMonth.withMonth(month))

    /**
     * 「年」のドロップダウンを表示する.
     *
     * @return 更新した[CalendarPickerUiState]
     */
    fun showYearDropDown(): CalendarPickerUiState = CalendarPickerUiState(now, true, monthDropDown, yearMonth)

    /**
     * 「月」のドロップダウンを表示する.
     *
     * @return 更新した[CalendarPickerUiState]
     */
    fun showMonthDropDown(): CalendarPickerUiState = CalendarPickerUiState(now, yearDropDown, true, yearMonth)

    /**
     * 「年」のドロップダウンを閉じる.
     *
     * @return 更新した[CalendarPickerUiState]
     */
    fun closeYearDropDown(): CalendarPickerUiState = CalendarPickerUiState(now, false, monthDropDown, yearMonth)

    /**
     * 「月」のドロップダウンを閉じる.
     *
     * @return 更新した[CalendarPickerUiState]
     */
    fun closeMonthDropDown(): CalendarPickerUiState = CalendarPickerUiState(now, yearDropDown, false, yearMonth)
}
