package io.github.kurramkurram.angerlog.ui.screen.analysis

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import io.github.kurramkurram.angerlog.ui.CustomDayOfWeek
import io.github.kurramkurram.angerlog.ui.component.chart.bar.BarData
import io.github.kurramkurram.angerlog.ui.component.chart.bar.BarItemDto
import java.time.YearMonth

class AnalysisAverage(private val yearMonth: YearMonth) {
    private val list = MutableList<MutableList<Int>>(7) { mutableListOf() }

    fun update(analysisItemOfDayDto: AnalysisItemOfDayDto) {
        val dayOfWeek = yearMonth.atDay(analysisItemOfDayDto.day).dayOfWeek.value
        val iDayOfWeek = CustomDayOfWeek().select(dayOfWeek)
        val index = iDayOfWeek.getValue()
        list[index].add(analysisItemOfDayDto.level)
    }

    @Composable
    fun getBarData(): BarData {
        val barItem = mutableListOf<BarItemDto>()
        val customDayOfWeek = CustomDayOfWeek()

        list.forEachIndexed { index, value ->
            val iDayOfWeek = customDayOfWeek.select(index)
            barItem.add(
                BarItemDto(
                    size = if (value.size == 0) 0f else value.average().toFloat(),
                    label = iDayOfWeek.getString(),
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                ),
            )
        }
        return BarData(items = barItem)
    }
}
