package io.github.kurramkurram.angerlog.ui.screen.home

sealed class HomeUiState {
    data object Loading : HomeUiState()

    data class Success(
        val angerLogList: List<HomeAngerLogDto> = listOf(),
        val hasAngerLog: Boolean = angerLogList.isNotEmpty(),
    ) : HomeUiState()

    data object Error : HomeUiState()
}
