package io.github.kurramkurram.angerlog.ui.screen.analysis

private const val SHOW_RATE_MINIMUM_COUNT = 1
private const val SHOW_AVERAGE_MINIMUM_COUNT = 1
private const val SHOW_TRANSITION_MINIMUM_COUNT = 3

sealed class AnalysisUiState {
    data object Loading : AnalysisUiState()

    data class Success(
        val recordCount: Int = 0,
        val lookBackCount: Int = 0,
        val rate: AnalysisRate,
        val averageOfDayOfWeek: AnalysisAverage,
        val transition: AnalysisTransition,
        val showRate: Boolean = recordCount >= SHOW_RATE_MINIMUM_COUNT,
        val showAverageOfDayWeek: Boolean = recordCount >= SHOW_AVERAGE_MINIMUM_COUNT,
        val showTransition: Boolean = recordCount >= SHOW_TRANSITION_MINIMUM_COUNT,
    ) : AnalysisUiState()

    data object Error : AnalysisUiState()
}
