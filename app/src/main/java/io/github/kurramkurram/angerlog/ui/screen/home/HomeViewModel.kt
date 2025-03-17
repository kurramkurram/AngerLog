package io.github.kurramkurram.angerlog.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.kurramkurram.angerlog.data.repository.AngerLogDataRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar

private const val SHOW_LIMIT_ITEM_COUNT = 20
private const val STOP_TIME_OUT_MILLIS = 500L

class HomeViewModel(angerLogDataRepository: AngerLogDataRepository) : ViewModel() {
    val state: StateFlow<HomeUiState> =
        angerLogDataRepository.getLimited(SHOW_LIMIT_ITEM_COUNT).map { data ->
            val calendar = Calendar.getInstance()
            val angerLogList = mutableListOf<HomeAngerLogDto>()
            data.forEach {
                val homeAngerLog = HomeAngerLogDto(angerLog = it, now = calendar.timeInMillis)
                angerLogList.add(homeAngerLog)
            }
            HomeUiState.Success(angerLogList = angerLogList)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIME_OUT_MILLIS),
            initialValue = HomeUiState.Loading,
        )
}
