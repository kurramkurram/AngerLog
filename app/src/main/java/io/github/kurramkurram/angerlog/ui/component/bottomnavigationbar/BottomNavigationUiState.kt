package io.github.kurramkurram.angerlog.ui.component.bottomnavigationbar

/**
 * ボトムナビゲーションバーの状態.
 */
sealed class BottomNavigationUiState {

    /**
     * ボトムナビゲーションバーの表示成功.
     *
     * @param settingBadge 設定にバッジを表示するかどうか
     */
    data class Success(
        val settingBadge: Boolean = false
    ) : BottomNavigationUiState()
}