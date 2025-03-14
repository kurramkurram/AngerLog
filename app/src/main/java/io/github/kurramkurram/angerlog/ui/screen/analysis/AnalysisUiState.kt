package io.github.kurramkurram.angerlog.ui.screen.analysis

import io.github.kurramkurram.angerlog.ui.AngerLevelType
import io.github.kurramkurram.angerlog.ui.component.chart.LineData

private const val SHOW_RATE_MINIMUM_COUNT = 1
private const val SHOW_AVERAGE_MINIMUM_COUNT = 1
private const val SHOW_TRANSITION_MINIMUM_COUNT = 3

sealed class AnalysisUiState {
    data object Loading : AnalysisUiState()

    data class Success(
        val recordCount: Int = 0,
        val lookBackCount: Int = 0,
        val rate: Map<AngerLevelType, Float> = emptyMap(),
        val dataCount: Int,
        val averageOfDayOfWeek: List<Float>,
        val transition: List<LineData>,
        val showRate: Boolean = dataCount >= SHOW_RATE_MINIMUM_COUNT,
        val showAverageOfDayWeek: Boolean = dataCount >= SHOW_AVERAGE_MINIMUM_COUNT,
        val showTransition: Boolean = dataCount >= SHOW_TRANSITION_MINIMUM_COUNT,
    ) : AnalysisUiState()

    data object Error : AnalysisUiState()
}
