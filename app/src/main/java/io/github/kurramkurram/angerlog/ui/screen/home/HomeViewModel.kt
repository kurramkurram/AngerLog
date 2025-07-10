package io.github.kurramkurram.angerlog.ui.screen.home

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.kurramkurram.angerlog.data.repository.AgreementPolicyRepository
import io.github.kurramkurram.angerlog.data.repository.AngerLogDataRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar

/**
 * 表示件数.
 */
private const val SHOW_LIMIT_ITEM_COUNT = 20

/**
 * データ取得のタイムアウト時間.
 */
private const val STOP_TIME_OUT_MILLIS = 500L

/**
 * ホーム画面のViewModel.
 *
 * @param angerLogDataRepository 怒りの記録のRepository
 * @param agreementPolicyRepository 利用規約の同時状態のRepository
 */
class HomeViewModel(
    private val angerLogDataRepository: AngerLogDataRepository,
    private val agreementPolicyRepository: AgreementPolicyRepository,
) : ViewModel() {
    var showNewPolicyDialog by mutableStateOf(false)
        private set

    val state: StateFlow<HomeUiState> =
        angerLogDataRepository.getLimited(SHOW_LIMIT_ITEM_COUNT).map { data ->
            val calendar = Calendar.getInstance()
            val angerLogList = mutableListOf<HomeAngerLog>()
            data.forEach {
                val homeAngerLog = HomeAngerLog(angerLog = it, now = calendar.timeInMillis)
                angerLogList.add(homeAngerLog)
            }
            HomeUiState.Success(logList = angerLogList)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIME_OUT_MILLIS),
            initialValue = HomeUiState.Loading,
        )

    /**
     * ダイアログを出すかどうかの判定を行う.
     *
     * @param context [Context]
     * @return true: 表示する
     */
    fun checkShowPolicyDialog(context: Context) {
        showNewPolicyDialog = !agreementPolicyRepository.hasAgreeLatest(context)
    }

    /**
     * 最新の利用規約に同意する.
     *
     * @param context [Context]
     */
    fun agreePolicy(context: Context) {
        showNewPolicyDialog = false
        agreementPolicyRepository.agreeLatest(context)
    }
}
