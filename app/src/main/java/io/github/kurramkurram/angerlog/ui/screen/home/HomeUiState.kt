package io.github.kurramkurram.angerlog.ui.screen.home

/**
 * ホーム画面の状態.
 */
sealed class HomeUiState {
    /**
     * 読み込み中.
     */
    data object Loading : HomeUiState()

    /**
     * データ取得の成功.
     *
     * @param angerLogList 怒りの記録
     * @param hasAngerLog 怒りの記録の有無
     */
    data class Success(
        val angerLogList: List<HomeAngerLog> = listOf(),
        val hasAngerLog: Boolean = angerLogList.isNotEmpty(),
    ) : HomeUiState()

    /**
     * 読み込みに失敗.
     */
    data object Error : HomeUiState()
}
