package io.github.kurramkurram.angerlog.ui.screen.analysis

/**
 * 分析ログで表示に使用するデータ.
 *
 * @param day 日にち
 * @param id データベースの一意のid
 * @param level 怒りの強さ
 * @param lookBackLevel 振り返りの怒りの強さ
 */
data class AnalysisItemOfDayDto(
    val day: Int,
    val id: Long,
    val level: Int,
    val lookBackLevel: Int,
)
