package io.github.kurramkurram.angerlog.data.repository

import android.content.Context
import io.github.kurramkurram.angerlog.data.dao.AngerLogDao
import io.github.kurramkurram.angerlog.data.database.AngerLogDatabase
import io.github.kurramkurram.angerlog.ui.screen.calendar.CalendarItemOfDayDto
import io.github.kurramkurram.angerlog.model.AngerLog
import io.github.kurramkurram.angerlog.ui.screen.analysis.AnalysisItemOfDayDto
import kotlinx.coroutines.flow.Flow
import java.time.YearMonth
import java.time.ZoneId

abstract class AngerLogDataRepository {
    abstract fun getAll(): Flow<List<AngerLog>>

    abstract fun getLimited(limit: Int): Flow<List<AngerLog>>

    abstract fun getCalenderItemByMonth(yearMonth: YearMonth): Flow<List<CalendarItemOfDayDto>>

    abstract fun getAnalysisItemByMonth(yearMonth: YearMonth): Flow<List<AnalysisItemOfDayDto>>

    abstract fun getById(id: Long): Flow<AngerLog?>

    abstract suspend fun save(angerLog: AngerLog)

    abstract suspend fun update(angerLog: AngerLog)

    abstract suspend fun delete(angerLog: AngerLog)
}

class AngerLogDataRepositoryImpl(
    private val context: Context,
    private val db: AngerLogDatabase = AngerLogDatabase.getDatabases(context),
    private val dao: AngerLogDao = db.angerLogDao(),
    private val zoneId: ZoneId = ZoneId.systemDefault()
) : AngerLogDataRepository() {
    override fun getAll(): Flow<List<AngerLog>> = dao.select()

    override fun getLimited(limit: Int): Flow<List<AngerLog>> = dao.selectLimited(limit)

    override fun getCalenderItemByMonth(yearMonth: YearMonth): Flow<List<CalendarItemOfDayDto>> {
        val begin = getBeginTimeOfMonth(yearMonth)
        val end = getEndTimeOfMonth(yearMonth)
        return dao.selectCalendarItemByPeriod(begin, end)
    }

    override fun getAnalysisItemByMonth(yearMonth: YearMonth): Flow<List<AnalysisItemOfDayDto>> {
        val begin = getBeginTimeOfMonth(yearMonth)
        val end = getEndTimeOfMonth(yearMonth)
        return dao.selectAnalysisItemByPeriod(begin, end)
    }

    override fun getById(id: Long): Flow<AngerLog?> = dao.select(id)

    override suspend fun save(angerLog: AngerLog) = dao.insert(angerLog)

    override suspend fun update(angerLog: AngerLog) = dao.update(angerLog)

    override suspend fun delete(angerLog: AngerLog) = dao.delete(angerLog)

    private fun getBeginTimeOfMonth(month: YearMonth): Long =
        month.atDay(1)
            .atTime(0, 0)
            .atZone(zoneId)
            .toEpochSecond() * 1000

    private fun getEndTimeOfMonth(month: YearMonth): Long = month.atDay(month.lengthOfMonth())
        .atTime(23, 59)
        .atZone(zoneId)
        .toEpochSecond() * 1000 + 999
}
