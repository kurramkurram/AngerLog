package io.github.kurramkurram.angerlog.ui.component.chart.pie

import androidx.compose.ui.graphics.Color

/**
 * 円グラフの各データ.
 *
 * @param rate 全体に占めるデータの割合
 * @param label ラベル
 * @param backgroundColor グラフの背景色
 */
data class PieItemDto(
    val rate: Float,
    val label: String = "",
    val backgroundColor: Color,
)
