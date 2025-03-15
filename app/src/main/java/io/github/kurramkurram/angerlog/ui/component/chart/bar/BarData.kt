package io.github.kurramkurram.angerlog.ui.component.chart.bar

class BarData(
    private val items: List<BarItemDto>,
) {
    fun getItemCount(): Int = items.size

    fun getItems(): List<BarItemDto> = items

    fun getMaxSize(): Float = items.maxOf { it.size }
}
