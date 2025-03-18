package io.github.kurramkurram.angerlog.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.github.kurramkurram.angerlog.ui.screen.calendar.CalendarItemOfDayDto
import io.github.kurramkurram.angerlog.model.AngerLog
import io.github.kurramkurram.angerlog.ui.screen.analysis.AnalysisItemOfDayDto
import kotlinx.coroutines.flow.Flow

@Dao
interface AngerLogDao {
    @Insert
    suspend fun insert(angerLog: AngerLog)

    @Update
    fun update(angerLog: AngerLog)

    @Delete
    fun delete(angerLog: AngerLog)

    @Query("SELECT * FROM t_anger_log")
    fun select(): Flow<List<AngerLog>>

    @Query("SELECT * FROM t_anger_log ORDER BY date DESC LIMIT :limit")
    fun selectLimited(limit: Int): Flow<List<AngerLog>>

    @Query("SELECT * FROM t_anger_log WHERE date BETWEEN :start AND :end")
    fun selectByPeriod(
        start: Long,
        end: Long,
    ): Flow<List<AngerLog>>

    @Query(
        """
       SELECT CAST(strftime('%d', date / 1000, 'unixepoch', 'localtime') AS INTEGER) AS day, id, level
        FROM t_anger_log WHERE date BETWEEN :start AND :end ORDER BY day 
    """,
    )
    fun selectCalendarItemByPeriod(
        start: Long,
        end: Long,
    ): Flow<List<CalendarItemOfDayDto>>

    @Query(
        """
       SELECT CAST(strftime('%d', date / 1000, 'unixepoch', 'localtime') AS INTEGER) AS day, id, level, lookBackLevel
        FROM t_anger_log WHERE date BETWEEN :start AND :end ORDER BY day 
    """,
    )
    fun selectAnalysisItemByPeriod(
        start: Long,
        end: Long,
    ): Flow<List<AnalysisItemOfDayDto>>

    @Query("SELECT * FROM t_anger_log WHERE id = :id")
    fun select(id: Long): Flow<AngerLog>
}
