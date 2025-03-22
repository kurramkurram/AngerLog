package io.github.kurramkurram.angerlog.ui.screen.analysis

import io.github.kurramkurram.angerlog.data.repository.AngerLogDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.YearMonth

/**
 * 分析画面に表示するデータを生成するユースケース.
 */
abstract class AnalysisDataUseCase {
    /**
     * 実行.
     *
     * @param yearMonth 分析画面に表示する年月
     * @return 分析画面に表示するデータ
     */
    abstract fun execute(yearMonth: YearMonth): Flow<AnalysisUiState.Success>
}

/**
 * 分析画面に表示するデータを生成するユースケース.
 *
 * @param
 */
class AnalysisDataUseCaseImpl(
    private val angerLogDataRepository: AngerLogDataRepository,
) : AnalysisDataUseCase() {
    /**
     * 実行.
     *
     * @param yearMonth 分析画面に表示する年月
     * @return 分析画面に表示するデータ
     */
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

            val recordCount = it.size
            val lookBackCount = it.filter { log -> log.lookBackLevel > 0 }.size

            AnalysisUiState.Success(
                recordCount = recordCount,
                lookBackCount = lookBackCount,
                rate = analysisRate,
                averageOfDayOfWeek = analysisAverage,
                transition = analysisTransition,
            )
        }
}
