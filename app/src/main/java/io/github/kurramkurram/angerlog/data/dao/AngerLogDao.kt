package io.github.kurramkurram.angerlog.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.github.kurramkurram.angerlog.model.AngerLog
import io.github.kurramkurram.angerlog.ui.screen.analysis.AnalysisItemOfDayDto
import io.github.kurramkurram.angerlog.ui.screen.calendar.CalendarItemOfDayDto
import kotlinx.coroutines.flow.Flow

@Dao
interface AngerLogDao {
    /**
     * 追加.
     *
     * @param angerLog 怒りの記録
     */
    @Insert
    suspend fun insert(angerLog: AngerLog)

    /**
     * 更新.
     *
     * @param angerLog 怒りの記録
     */
    @Update
    fun update(angerLog: AngerLog)

    /**
     * 削除.
     *
     * @param angerLog 怒りの記録
     */
    @Delete
    fun delete(angerLog: AngerLog)

    /**
     * 全件取得.
     *
     * @return 怒りの記録
     */
    @Query("SELECT * FROM t_anger_log")
    fun select(): Flow<List<AngerLog>>

    /**
     * 指定件数分、新しいものから取得する.
     *
     * @param limit 件数
     * @return 怒りの記録
     */
    @Query("SELECT * FROM t_anger_log ORDER BY date DESC LIMIT :limit")
    fun selectLimited(limit: Int): Flow<List<AngerLog>>

    /**
     * 指定期間、新しいものから取得する.
     *
     * @param start 指定期間の開始
     * @param end 指定期間の終了
     * @return 怒りの記録
     */
    @Query("SELECT * FROM t_anger_log WHERE date BETWEEN :start AND :end")
    fun selectByPeriod(
        start: Long,
        end: Long,
    ): Flow<List<AngerLog>>

    /**
     * 指定期間、新しいものからカレンダー画面の情報（日付・id・怒りの強さ）を取得する.
     *
     * @param start 指定期間の開始
     * @param end 指定期間の終了
     * @return カレンダーに表示する情報
     */
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

    /**
     * 指定期間、新しいものから分析画面の情報（日付・id・怒りの強さ）を取得する.
     *
     * @param start 指定期間の開始
     * @param end 指定期間の終了
     * @return 分析画面に表示する情報
     */
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

    /**
     * 指定idに一致するものを取得する.
     *
     * @param id idに一致する
     * @return 怒りの情報
     */
    @Query("SELECT * FROM t_anger_log WHERE id = :id")
    fun select(id: Long): Flow<AngerLog>
}
