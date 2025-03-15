package io.github.kurramkurram.angerlog.ui.component.chart.bar

import androidx.compose.ui.graphics.Color

data class BarItemDto(
    val size: Float,
    val label: String,
    val backgroundColor: Color = Color.Red,
)
