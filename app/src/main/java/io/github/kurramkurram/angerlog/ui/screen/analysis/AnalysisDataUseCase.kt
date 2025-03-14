package io.github.kurramkurram.angerlog.ui.screen.analysis

import io.github.kurramkurram.angerlog.data.repository.AngerLogDataRepository
import io.github.kurramkurram.angerlog.ui.AngerLevel
import io.github.kurramkurram.angerlog.ui.AngerLevelType
import io.github.kurramkurram.angerlog.ui.component.chart.LineData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.YearMonth

abstract class AnalysisDataUseCase {
    abstract fun execute(yearMonth: YearMonth): Flow<AnalysisUiState.Success>
}

class AnalysisDataUseCaseImpl(
    private val angerLogDataRepository: AngerLogDataRepository,
) : AnalysisDataUseCase() {
    override fun execute(yearMonth: YearMonth): Flow<AnalysisUiState.Success> =
        angerLogDataRepository.getCalenderItemByMonth(yearMonth = yearMonth).map {
            val map: MutableMap<AngerLevelType, Int> = mutableMapOf()
            AngerLevelType.entries.forEach { type -> map[type] = 0 }

            val dataOfDayOfWeek = MutableList<MutableList<Int>>(7) { mutableListOf() }

            val transition = mutableListOf<LineData>()
            val dataOfDay = mutableListOf<Int>()
            var day = 0
            val angerLevel = AngerLevel()
            it.forEach { item ->
                val level = item.level

                val angerLevelType = angerLevel.getAngerLevelType(item.level)
                val count = map[angerLevelType]
                map[angerLevelType] = (count ?: 0) + 1

                val dayOfWeek = yearMonth.atDay(item.day).dayOfWeek.value
                dataOfDayOfWeek[dayOfWeek].add(level)

                if (day != item.day) {
                    if (dataOfDay.size > 0) {
                        val averageOfDay = dataOfDay.average().toFloat()
                        transition.add(LineData(day, averageOfDay))
                        dataOfDay.clear()
                    }
                    day = item.day
                }
                dataOfDay.add(level)
            }

            val sum = map.values.sum()
            val rate: MutableMap<AngerLevelType, Float> = mutableMapOf()
            if (sum > 0) {
                map.forEach { (angerLevelType, i) ->
                    rate[angerLevelType] = i.toFloat() * 100 / sum
                }
            }

            val averageAngerOfDayOfWeek = mutableListOf<Float>()
            dataOfDayOfWeek.forEach { dayOfWeek ->
                val size = if (dayOfWeek.size == 0) 0f else dayOfWeek.average().toFloat()
                averageAngerOfDayOfWeek.add(size)
            }
            AnalysisUiState.Success(
                recordCount = it.size,
                lookBackCount = 10, // TODO
                rate = rate,
                dataCount = it.size,
                averageOfDayOfWeek = averageAngerOfDayOfWeek,
                transition = transition,
            )
        }
}
