package io.github.kurramkurram.angerlog.ui.screen.analysis

import io.github.kurramkurram.angerlog.data.repository.AngerLogDataRepository
import io.github.kurramkurram.angerlog.ui.AngerLevel
import io.github.kurramkurram.angerlog.ui.AngerLevelType
import io.github.kurramkurram.angerlog.ui.component.chart.line.LineData
import io.github.kurramkurram.angerlog.ui.component.chart.line.LineItemDto
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
        angerLogDataRepository.getAnalysisItemByMonth(yearMonth = yearMonth).map {
            val map: MutableMap<AngerLevelType, Int> = mutableMapOf()
            AngerLevelType.entries.forEach { type -> map[type] = 0 }

            val dataOfDayOfWeek = MutableList<MutableList<Int>>(7) { mutableListOf() }

            val transition = mutableListOf<LineItemDto>()
            val dataOfDay = mutableListOf<Int>()
            var day = 0
            val angerLevel = AngerLevel()

            fun updateTransition() {
                if (dataOfDay.size > 0) {
                    val averageOfDay = dataOfDay.average().toFloat()
                    transition.add(LineItemDto(day, averageOfDay))
                    dataOfDay.clear()
                }
            }

            it.forEach { item ->
                val level = item.level

                val angerLevelType = angerLevel.getAngerLevelType(item.level)
                val count = map[angerLevelType]
                map[angerLevelType] = (count ?: 0) + 1

                val dayOfWeek = yearMonth.atDay(item.day).dayOfWeek.value
                dataOfDayOfWeek[dayOfWeek].add(level)

                if (day != item.day) {
                    updateTransition()
                    day = item.day
                }
                dataOfDay.add(level)
            }
            updateTransition()

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

            val lookBackCount = it.filter { log -> log.lookBackLevel > 0 }.size

            AnalysisUiState.Success(
                recordCount = it.size,
                lookBackCount = lookBackCount,
                rate = rate,
                dataCount = it.size,
                averageOfDayOfWeek = averageAngerOfDayOfWeek,
                transition = LineData(transition),
            )
        }
}
