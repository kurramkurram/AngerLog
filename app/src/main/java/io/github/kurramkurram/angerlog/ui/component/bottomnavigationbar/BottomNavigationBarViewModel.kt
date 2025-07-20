package io.github.kurramkurram.angerlog.ui.component.bottomnavigationbar

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.kurramkurram.angerlog.data.repository.NewsRepository
import io.github.kurramkurram.angerlog.data.repository.TipsRepository
import io.github.kurramkurram.angerlog.util.L
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ボトムナビゲーションバーのViewModel.
 *
 * @param tipsRepository Tips表示状態のRepository
 * @param newsRepository お知らせのRepository
 */
class BottomNavigationBarViewModel(
    private val tipsRepository: TipsRepository,
    private val newsRepository: NewsRepository,
) : ViewModel() {
    private val _state: MutableStateFlow<BottomNavigationUiState> =
        MutableStateFlow(BottomNavigationUiState.Success())
    val state = _state.asStateFlow()

    /**
     *　バッジの状態の判定.
     *
     * @param context [Context]
     */
    fun checkBadgeStatus(context: Context) {
        viewModelScope.launch {
            val flow = tipsRepository.isUnreadTipsExist(context)
                .combine(newsRepository.isUnreadNewsExist()) { tips, news ->
                    tips || news
                }
            flow.collect { badge ->
                _state.update { BottomNavigationUiState.Success(settingBadge = badge) }
            }
        }
    }
}
