package io.github.kurramkurram.angerlog.ui.component.chart.bar

import androidx.compose.ui.graphics.Color

/**
 * 棒グラフの各棒のデータ.
 *
 * @param size 値
 * @param label ラベル
 * @param backgroundColor グラフの色
 */
data class BarItemDto(
    val size: Float,
    val label: String,
    val backgroundColor: Color = Color.Red,
)
