package io.github.kurramkurram.angerlog.ui.screen.news

import io.github.kurramkurram.angerlog.model.NewsItem

/**
 * お知らせ画面の状態.
 */
sealed class NewsUiState {
    /**
     * お知らせ画面の成功状態.
     *
     * @param newsList お知らせ一覧
     */
    data class Success(
        val newsList: List<NewsItem> = listOf()
    ) : NewsUiState()
}