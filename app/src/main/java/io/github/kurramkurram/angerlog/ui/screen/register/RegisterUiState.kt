package io.github.kurramkurram.angerlog.ui.screen.register

/**
 * 登録画面の状態.
 */
sealed class RegisterUiState {
    /**
     * データ取得の成功.
     *
     * @param showDatePicker 日付選択ダイアログの表示状態
     * @param showTimePicker 時間選択ダイアログの表示状態
     * @param showBackDialog 戻るダイアログの表示状態
     * @param showDeleteDialog 削除ダイアログの表示状態
     * @param showBadDateDialog 日付エラーダイアログの表示状態
     * @param showBottomSheet ボトムシートの表示状態
     * @param goBack 戻れるかどうかの状態
     */
    data class Success(
        val showDatePicker: Boolean = false,
        val showTimePicker: Boolean = false,
        val showBackDialog: Boolean = false,
        val showDeleteDialog: Boolean = false,
        val showBadDateDialog: Boolean = false,
        val showBottomSheet: Boolean = false,
        val goBack: Boolean = false,
    ) : RegisterUiState()
}
