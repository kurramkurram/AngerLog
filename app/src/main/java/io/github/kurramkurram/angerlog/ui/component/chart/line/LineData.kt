package io.github.kurramkurram.angerlog.ui.component.chart.line

class LineData(private val lineData: List<LineItemDto>) {
    fun getMaxY(): Float = lineData.maxOf { it.y }

    fun getMinY(): Float = lineData.minOf { it.y }

    fun getItemCount(): Int = lineData.size

    fun getItems(): List<LineItemDto> = lineData
}
