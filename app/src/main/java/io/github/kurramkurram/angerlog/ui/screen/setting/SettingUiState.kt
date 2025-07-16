package io.github.kurramkurram.angerlog.ui.screen.setting

/**
 * 設定画面の状態.
 */
sealed class SettingUiState {
    /**
     * 設定画面の表示成功.
     *
     * @param tipsBadge Tipsにバッジを表示する
     */
    data class Success(
        val tipsBadge: Boolean = false,
    ) : SettingUiState()
}
