package io.github.kurramkurram.angerlog.ui.screen.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.kurramkurram.angerlog.data.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * お知らせ画面のViewModel.
 *
 * @param newsRepository お知らせのRepository
 */
class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    private val _state = MutableStateFlow<NewsUiState>(NewsUiState.Success())
    val state = _state.asStateFlow()

    init {
        loadNews()
    }

    /**
     * お知らせの読み込み.
     */
    private fun loadNews() {
        viewModelScope.launch {
            newsRepository.newsList.collect {
                _state.value = NewsUiState.Success(newsList = it)
            }
        }
    }
}
