package io.github.kurramkurram.angerlog.data.repository

import android.content.Context
import io.github.kurramkurram.angerlog.data.dao.AngerLogDao
import io.github.kurramkurram.angerlog.data.database.AngerLogDatabase
import io.github.kurramkurram.angerlog.model.AngerLog
import io.github.kurramkurram.angerlog.ui.screen.analysis.AnalysisItemOfDayDto
import io.github.kurramkurram.angerlog.ui.screen.calendar.CalendarItemOfDayDto
import kotlinx.coroutines.flow.Flow
import java.time.YearMonth
import java.time.ZoneId

/**
 * 怒りの記録を管理するRepository.
 */
abstract class AngerLogDataRepository {
    /**
     * 追加.
     *
     * @param angerLog 怒りの記録
     */
    abstract suspend fun save(angerLog: AngerLog)

    /**
     * 更新.
     *
     * @param angerLog 怒りの記録
     */
    abstract suspend fun update(angerLog: AngerLog)

    /**
     * 削除.
     *
     * @param angerLog 怒りの記録
     */
    abstract suspend fun delete(angerLog: AngerLog)

    /**
     * 全件取得する.
     *
     * @return 怒りの記録
     */
    abstract fun getAll(): Flow<List<AngerLog>>

    /**
     * 指定件数、新しいものから取得する.
     *
     * @param limit 件数
     * @return 怒りの記録
     */
    abstract fun getLimited(limit: Int): Flow<List<AngerLog>>

    /**
     * 指定件年月。新しいものからカレンダー画面の情報（日付・id・怒りの強さ）を取得する.
     *
     * @param yearMonth 取得したい年月
     * @return カレンダーに表示する情報
     */
    abstract fun getCalenderItemByMonth(yearMonth: YearMonth): Flow<List<CalendarItemOfDayDto>>

    /**
     * 指定件年月。新しいものからカレンダー画面の情報（日付・id・怒りの強さ）を取得する.
     *
     * @param yearMonth 取得したい年月
     * @return 分析画面に表示する情報
     */
    abstract fun getAnalysisItemByMonth(yearMonth: YearMonth): Flow<List<AnalysisItemOfDayDto>>

    /**
     * 指定idに一致するものを取得する.
     *
     * @param id idに一致する
     * @return 怒りの情報
     */
    abstract fun getById(id: Long): Flow<AngerLog?>
}

class AngerLogDataRepositoryImpl(
    context: Context,
    private val db: AngerLogDatabase = AngerLogDatabase.getDatabases(context),
    private val dao: AngerLogDao = db.angerLogDao(),
    private val zoneId: ZoneId = ZoneId.systemDefault(),
) : AngerLogDataRepository() {
    /**
     * 追加.
     *
     * @param angerLog 怒りの記録
     */
    override suspend fun save(angerLog: AngerLog) = dao.insert(angerLog)

    /**
     * 更新.
     *
     * @param angerLog 怒りの記録
     */
    override suspend fun update(angerLog: AngerLog) = dao.update(angerLog)

    /**
     * 削除.
     *
     * @param angerLog 怒りの記録
     */
    override suspend fun delete(angerLog: AngerLog) = dao.delete(angerLog)

    /**
     * 全件取得する.
     *
     * @return 怒りの記録
     */
    override fun getAll(): Flow<List<AngerLog>> = dao.select()

    /**
     * 指定件数、新しいものから取得する.
     *
     * @param limit 件数
     * @return 怒りの記録
     */
    override fun getLimited(limit: Int): Flow<List<AngerLog>> = dao.selectLimited(limit)

    /**
     * 指定件年月。新しいものからカレンダー画面の情報（日付・id・怒りの強さ）を取得する.
     *
     * @param yearMonth 取得したい年月
     * @return カレンダーに表示する情報
     */
    override fun getCalenderItemByMonth(yearMonth: YearMonth): Flow<List<CalendarItemOfDayDto>> {
        val begin = getBeginTimeOfMonth(yearMonth)
        val end = getEndTimeOfMonth(yearMonth)
        return dao.selectCalendarItemByPeriod(begin, end)
    }

    /**
     * 指定件年月。新しいものからカレンダー画面の情報（日付・id・怒りの強さ）を取得する.
     *
     * @param yearMonth 取得したい年月
     * @return 分析画面に表示する情報
     */
    override fun getAnalysisItemByMonth(yearMonth: YearMonth): Flow<List<AnalysisItemOfDayDto>> {
        val begin = getBeginTimeOfMonth(yearMonth)
        val end = getEndTimeOfMonth(yearMonth)
        return dao.selectAnalysisItemByPeriod(begin, end)
    }

    /**
     * 指定idに一致するものを取得する.
     *
     * @param id idに一致する
     * @return 怒りの情報
     */
    override fun getById(id: Long): Flow<AngerLog?> = dao.select(id)

    private fun getBeginTimeOfMonth(month: YearMonth): Long =
        month.atDay(1)
            .atTime(0, 0)
            .atZone(zoneId)
            .toEpochSecond() * 1000

    private fun getEndTimeOfMonth(month: YearMonth): Long =
        month.atDay(month.lengthOfMonth())
            .atTime(23, 59)
            .atZone(zoneId)
            .toEpochSecond() * 1000 + 999
}
