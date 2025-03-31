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
     * @param logList 怒りの記録
     * @param angerLogList 怒りの記録（振り返りなし）
     * @param hasAngerLog 怒りの記録（振り返りなし）の有無
     * @param lookBackList 怒りの記録（振り返りあり）
     * @param hasLookBack 怒りの記録（振り返りあり）の有無
     */
    data class Success(
        val logList: List<HomeAngerLog> = listOf(),
        val angerLogList: List<HomeAngerLog> = logList.filter { !it.canShowLookBack() },
        val hasAngerLog: Boolean = angerLogList.isNotEmpty(),
        val lookBackList: List<HomeAngerLog> = logList.filter { it.canShowLookBack() },
        val hasLookBack: Boolean = lookBackList.isNotEmpty(),
    ) : HomeUiState()

    /**
     * 読み込みに失敗.
     */
    data object Error : HomeUiState()
}
