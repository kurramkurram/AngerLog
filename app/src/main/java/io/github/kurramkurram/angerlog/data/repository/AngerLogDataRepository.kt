package io.github.kurramkurram.angerlog.data.repository

import android.content.Context
import io.github.kurramkurram.angerlog.data.dao.AngerLogDao
import io.github.kurramkurram.angerlog.data.database.AngerLogDatabase
import io.github.kurramkurram.angerlog.model.AngerIdOfDayDto
import io.github.kurramkurram.angerlog.model.AngerLog
import kotlinx.coroutines.flow.Flow
import java.time.YearMonth
import java.time.ZoneId

interface AngerLogDataRepository {
    fun getAll(): Flow<List<AngerLog>>

    fun getLimited(limit: Int): Flow<List<AngerLog>>

    fun getCalenderItemByMonth(yearMonth: YearMonth): Flow<List<AngerIdOfDayDto>>

    fun getById(id: Long): Flow<AngerLog?>

    suspend fun save(angerLog: AngerLog)

    suspend fun update(angerLog: AngerLog)

    suspend fun delete(angerLog: AngerLog)
}

class AngerLogDataRepositoryImpl(
    private val context: Context,
    private val db: AngerLogDatabase = AngerLogDatabase.getDatabases(context),
    private val dao: AngerLogDao = db.angerLogDao(),
) : AngerLogDataRepository {
    override fun getAll(): Flow<List<AngerLog>> = dao.select()

    override fun getLimited(limit: Int): Flow<List<AngerLog>> = dao.selectLimited(limit)

    override fun getCalenderItemByMonth(yearMonth: YearMonth): Flow<List<AngerIdOfDayDto>> {
        val zoneId = ZoneId.systemDefault()
        val begin =
            yearMonth.atDay(1)
                .atTime(0, 0)
                .atZone(zoneId)
                .toEpochSecond() * 1000
        val end =
            yearMonth
                .atDay(yearMonth.lengthOfMonth())
                .atTime(23, 59)
                .atZone(zoneId)
                .toEpochSecond() * 1000 + 999
        return dao.selectIdByPeriod(begin, end)
    }

    override fun getById(id: Long): Flow<AngerLog?> = dao.select(id)

    override suspend fun save(angerLog: AngerLog) = dao.insert(angerLog)

    override suspend fun update(angerLog: AngerLog) = dao.update(angerLog)

    override suspend fun delete(angerLog: AngerLog) = dao.delete(angerLog)
}
