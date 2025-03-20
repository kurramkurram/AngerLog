package io.github.kurramkurram.angerlog.ui.screen.analysis

import io.github.kurramkurram.angerlog.data.repository.AngerLogDataRepository
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
            val analysisRate = AnalysisRate()
            val analysisAverage = AnalysisAverage(yearMonth = yearMonth)
            val analysisTransition = AnalysisTransition()

            it.forEach { analysisItemOfDayDto ->
                analysisRate.update(level = analysisItemOfDayDto.level)
                analysisAverage.update(analysisItemOfDayDto = analysisItemOfDayDto)
                analysisTransition.update(analysisItemOfDayDto = analysisItemOfDayDto)
            }

            val lookBackCount = it.filter { log -> log.lookBackLevel > 0 }.size

            AnalysisUiState.Success(
                recordCount = it.size,
                lookBackCount = lookBackCount,
                rate = analysisRate,
                averageOfDayOfWeek = analysisAverage,
                transition = analysisTransition,
            )
        }
}
