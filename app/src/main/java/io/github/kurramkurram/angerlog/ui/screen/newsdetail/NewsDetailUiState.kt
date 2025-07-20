package io.github.kurramkurram.angerlog.ui.screen.newsdetail

/**
 * お知らせ詳細画面の状態.
 */
sealed class NewsDetailUiState {

    /**
     * お知らせ詳細画面の成功状態.
     *
     * @param newsId　お知らせのid
     * @param title お知らせのタイトル
     * @param description お知らせの説明
     */
    data class Success(
        val newsId: Int,
        val title: String,
        val description: String,
    ) : NewsDetailUiState()

    /**
     * お知らせ詳細画面の読み込み中.
     */
    data object Loading : NewsDetailUiState()
}
