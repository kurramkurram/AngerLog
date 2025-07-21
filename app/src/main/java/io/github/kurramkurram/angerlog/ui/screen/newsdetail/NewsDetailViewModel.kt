package io.github.kurramkurram.angerlog.ui.screen.newsdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.kurramkurram.angerlog.data.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * お知らせ詳細のViewModel.
 *
 * @param newsRepository お知らせのRepository.
 */
class NewsDetailViewModel(private val newsRepository: NewsRepository) :
    ViewModel() {
    private val _state = MutableStateFlow<NewsDetailUiState>(NewsDetailUiState.Loading)
    val state = _state.asStateFlow()

    /**
     * お知らせ詳細の初期化処理.
     *
     * @param newsId お知らせ一覧で選択されたお知らせのid
     */
    fun initialize(newsId: Int) {
        viewModelScope.launch {
            newsRepository.markAsRead(newsId = newsId)

            val news = newsRepository.getNews(newsId = newsId)
            _state.value =
                NewsDetailUiState.Success(
                    newsId = newsId,
                    title = news.title,
                    description = news.description,
                )
        }
    }
}
