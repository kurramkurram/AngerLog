package io.github.kurramkurram.angerlog.ui.screen.calendar

import java.util.Calendar

/**
 * カレンダー画面の状態.
 */
sealed class CalendarUiState {
    /**
     * 読み込み中.
     */
    data object Loading : CalendarUiState()

    /**
     * データの取得成功.
     *
     * @param calendarItemList カレンダー画面のひと月のデータ
     * @param today カレンダー画面の表示日にち
     * @param showDialog カレンダー画面の選択ダイアログの表示状態
     * @param selectDay カレンダーで選択した日にち
     */
    data class Success(
        val calendarItemList: List<CalendarIdListOfDayDto?> = emptyList(),
        val today: Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
        val showDialog: Boolean = false,
        val selectDay: Int = 0,
    ) : CalendarUiState()

    /**
     * 読み込みに失敗.
     */
    data object Error : CalendarUiState()
}
