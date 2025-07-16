package io.github.kurramkurram.angerlog.ui.component.bottomnavigationbar

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.kurramkurram.angerlog.data.repository.TipsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ボトムナビゲーションバーのViewModel.
 *
 * @param tipsRepository Tips表示状態のRepository
 */
class BottomNavigationBarViewModel(private val tipsRepository: TipsRepository) : ViewModel() {
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
            tipsRepository.isUnreadTipsExist(context).map {
                it
            }.collect { badge ->
                _state.update { BottomNavigationUiState.Success(settingBadge = badge) }
            }
        }
    }
}
