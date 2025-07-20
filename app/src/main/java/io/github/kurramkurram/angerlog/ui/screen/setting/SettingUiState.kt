package io.github.kurramkurram.angerlog.ui.screen.setting

/**
 * 設定画面の状態.
 */
sealed class SettingUiState {
    /**
     * 設定画面の成功状態.
     *
     * @param tipsBadge Tipsにバッジを表示する
     * @param newsBadge お知らせにバッジを表示する
     */
    data class Success(
        val tipsBadge: Boolean = false,
        val newsBadge: Boolean = false,
    ) : SettingUiState()
}
