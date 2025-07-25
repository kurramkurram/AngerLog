package io.github.kurramkurram.angerlog.ui.screen.setting

import android.appwidget.AppWidgetManager
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.kurramkurram.angerlog.data.repository.NewsRepository
import io.github.kurramkurram.angerlog.data.repository.TipsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * 設定画面のViewModel.
 *
 * @param context [Context]
 * @param tipsRepository Tips表示状態のRepository
 * @param newsRepository お知らせのRepository
 */
class SettingViewModel(
    context: Context,
    private val tipsRepository: TipsRepository,
    private val newsRepository: NewsRepository,
) : ViewModel() {
    private val _state: MutableStateFlow<SettingUiState> =
        MutableStateFlow(SettingUiState.Success())
    val state = _state.asStateFlow()

    init {
        checkAddWidgetItem(context)
        checkTipsBadge(context)
        checkNewsBadge()
    }

    private fun checkAddWidgetItem(context: Context) {
        val manager = AppWidgetManager.getInstance(context)
        val supportWidget = manager.isRequestPinAppWidgetSupported
        _state.update { (it as SettingUiState.Success).copy(showWidgetItem = supportWidget) }
    }

    /**
     * Tipsの項目にバッジを表示するかの判定.
     *
     * @param context [Context]
     */
    private fun checkTipsBadge(context: Context) {
        viewModelScope.launch {
            tipsRepository.isUnreadTipsExist(context).map {
                it
            }.collect { badge ->
                _state.update { (it as SettingUiState.Success).copy(tipsBadge = badge) }
            }
        }
    }

    /**
     * お知らせの項目にバッジを表示するかの判定.
     */
    private fun checkNewsBadge() {
        viewModelScope.launch {
            newsRepository.isUnreadNewsExist().map {
                it
            }.collect { badge ->
                _state.update { (it as SettingUiState.Success).copy(newsBadge = badge) }
            }
        }
    }
}
