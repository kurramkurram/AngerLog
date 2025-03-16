package io.github.kurramkurram.angerlog.ui.screen.home

import io.github.kurramkurram.angerlog.model.AngerLog
import io.github.kurramkurram.angerlog.util.DateConverter.Companion.dateToString

sealed class HomeUiState {
    data object Loading : HomeUiState()

    data class Success(
        val angerLogList: List<HomeAngerLogDto> = listOf(),
        val hasAngerLog: Boolean = angerLogList.isNotEmpty(),
    ) : HomeUiState()

    data object Error : HomeUiState()
}

class HomeAngerLogDto(
    val angerLog: AngerLog,
    now: Long,
) {
    val showLookBackIcon: Boolean

    init {
        fun isShowLookBackIcon(): Boolean {
            val diff = now - angerLog.date.time
            return diff / (24 * 60 * 60 * 1000) > 3
        }

        showLookBackIcon = isShowLookBackIcon()
    }

    fun getId(): Long = angerLog.id

    fun getDate(): String = angerLog.date.dateToString()

    fun getEvent(): String = angerLog.event

    fun getLevel(): Int = angerLog.level
}
