package io.github.kurramkurram.angerlog.ui.component.chart.pie

import androidx.compose.ui.graphics.Color

data class PieItemDto(
    val rate: Float,
    val label: String = "",
    val backgroundColor: Color,
)