package io.github.kurramkurram.angerlog.ui.screen.calendar

import io.github.kurramkurram.angerlog.data.repository.AngerLogDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.YearMonth

/**
 * カレンダー画面に表示するデータを生成するユースケース.
 */
abstract class CalendarDataUseCase {

    /**
     * 実行.
     *
     * @return yearMonth 分析画面に表示する年月
     * @return カレンダー画面に表示するデータ
     */
    abstract fun execute(yearMonth: YearMonth): Flow<CalendarUiState.Success>
}

class CalendarDataUseCaseImpl(private val angerLogDataRepository: AngerLogDataRepository) :
    CalendarDataUseCase() {
    /**
     * 実行.
     *
     * @return yearMonth 分析画面に表示する年月
     * @return カレンダー画面に表示するデータ
     */
    override fun execute(yearMonth: YearMonth): Flow<CalendarUiState.Success> =
        angerLogDataRepository.getCalenderItemByMonth(yearMonth).map {
            val items = MutableList<CalendarIdListOfDayDto?>(yearMonth.lengthOfMonth()) { null }
            var day = 0
            var ids = mutableListOf<Pair<Long, Int>>()
            for (i in it) {
                if (day != i.day) {
                    if (ids.size > 0) {
                        items[day - 1] = CalendarIdListOfDayDto(day, ids)
                        ids = mutableListOf()
                    }
                    day = i.day
                }
                ids.add(Pair(i.id, i.level))
            }
            if (ids.size > 0) {
                items[day - 1] = CalendarIdListOfDayDto(day, ids)
            }
            CalendarUiState.Success(calendarItemList = items)
        }
}
