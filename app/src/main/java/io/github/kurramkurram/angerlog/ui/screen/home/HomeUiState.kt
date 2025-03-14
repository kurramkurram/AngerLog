package io.github.kurramkurram.angerlog.ui.screen.home

import io.github.kurramkurram.angerlog.model.AngerLog

sealed class HomeUiState {
    data object Loading : HomeUiState()

    data class Success(
        val angerLogList: List<AngerLog> = listOf(),
        val hasAngerLog: Boolean = angerLogList.isNotEmpty(),
    ) : HomeUiState()

    data object Error : HomeUiState()
}
