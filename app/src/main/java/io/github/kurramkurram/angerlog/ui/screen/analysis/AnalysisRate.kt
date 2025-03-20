package io.github.kurramkurram.angerlog.ui.screen.analysis

import androidx.compose.runtime.Composable
import io.github.kurramkurram.angerlog.ui.AngerLevel
import io.github.kurramkurram.angerlog.ui.AngerLevelType
import io.github.kurramkurram.angerlog.ui.component.chart.pie.PieData
import io.github.kurramkurram.angerlog.ui.component.chart.pie.PieItemDto

class AnalysisRate {
    private val list: MutableList<Int> = MutableList(AngerLevelType.entries.size) { 0 }

    fun update(level: Int) {
        if (level < 0 ||
            level > AngerLevel.AngerLevel5().getValue()
        ) throw IllegalArgumentException()

        list[level - 1]++
    }

    @Composable
    fun getPieData(): PieData {
        val angerLevel = AngerLevel()
        val pieces = mutableListOf<PieItemDto>()
        val sum = list.sum()
        if (sum == 0) return PieData(pieItems = emptyList())
        val rate = mutableListOf<Float>()
        list.forEachIndexed { index, count ->
            val angerLevelValue = index + 1
            val level = angerLevel.select(angerLevelValue)
            pieces.add(
                PieItemDto(
                    rate = count.toFloat() * 100 / sum,
                    label = angerLevelValue.toString(),
                    backgroundColor = level.getColor()
                )
            )
            rate.add(count.toFloat() * 100 / sum)
        }

        return PieData(pieItems = pieces)
    }
}
