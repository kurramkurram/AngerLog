package io.github.kurramkurram.angerlog.ui.component.chart.pie

data class PieData(private val pieItems: List<PieItemDto>) {
    fun getItemCount(): Int = pieItems.size

    fun getItems(): List<PieItemDto> = pieItems

    fun getItem(index: Int): PieItemDto {
        if (index < 0 || index >= getItemCount()) throw IllegalArgumentException()
        return pieItems[index]
    }
}