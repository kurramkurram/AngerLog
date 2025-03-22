package io.github.kurramkurram.angerlog.ui.screen.analysis

private const val SHOW_RATE_MINIMUM_COUNT = 1
private const val SHOW_AVERAGE_MINIMUM_COUNT = 1
private const val SHOW_TRANSITION_MINIMUM_COUNT = 3

/**
 * 分析画面の状態.
 */
sealed class AnalysisUiState {
    /**
     * 読み込み中.
     */
    data object Loading : AnalysisUiState()

    /**
     * データ取得の成功.
     *
     * @param recordCount 怒りの記録件数
     * @param lookBackCount 振り返りの記録件数
     * @param rate 怒りの割合を管理するデータ
     * @param averageOfDayOfWeek 曜日別の怒りの強さを管理するデータ
     * @param transition 日別の怒りの強さを管理するデータ
     * @param showRate 怒りの割合のグラフを表示するかどうかの判定
     * @param showAverageOfDayWeek 曜日別の怒りの強さのグラフを表示するかどうかの判定
     * @param showTransition 日別の怒りの強さのグラフを表示するかどうかの判定
     */
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

    /**
     * 読み込みに失敗.
     */
    data object Error : AnalysisUiState()
}
