package io.github.kurramkurram.angerlog.ui.screen.analysis

import androidx.compose.runtime.Composable
import io.github.kurramkurram.angerlog.ui.AngerLevel
import io.github.kurramkurram.angerlog.ui.AngerLevelType
import io.github.kurramkurram.angerlog.ui.component.chart.pie.PieData
import io.github.kurramkurram.angerlog.ui.component.chart.pie.PieItemDto

/**
 * 分析画面の怒りの強さ別の割合を管理する.
 */
class AnalysisRate {
    private val list: MutableList<Int> = MutableList(AngerLevelType.entries.size) { 0 }

    /**
     * 怒りの強さを保持する.
     *
     * @param level 怒りの強さ
     */
    fun update(level: Int) {
        if (level < 0 ||
            level > AngerLevel.AngerLevel5().getValue()
        ) {
            throw IllegalArgumentException()
        }

        list[level - 1]++
    }

    /**
     * 円グラフのデータを生成する.
     *
     * @return 円グラフのデータ
     */
    @Composable
    fun createPieData(): PieData {
        val angerLevel = AngerLevel()
        val sum = list.sum()
        if (sum == 0) return PieData(pieItems = emptyList())

        val pieces = mutableListOf<PieItemDto>()
        list.forEachIndexed { index, count ->
            val angerLevelValue = index + 1
            val level = angerLevel.select(angerLevelValue)
            pieces.add(
                PieItemDto(
                    rate = count.toFloat() * 100 / sum,
                    label = angerLevelValue.toString(),
                    backgroundColor = level.getColor(),
                ),
            )
        }
        return PieData(pieItems = pieces)
    }
}
