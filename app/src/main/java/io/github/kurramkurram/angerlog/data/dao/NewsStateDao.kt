package io.github.kurramkurram.angerlog.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.kurramkurram.angerlog.model.NewsState
import kotlinx.coroutines.flow.Flow

/**
 * お知らせの既読状態データベースインターフェース.
 */
@Dao
interface NewsStateDao {
    /**
     * 既読の書き込み.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(newsState: NewsState)

    /**
     * 全件取得.
     */
    @Query("SELECT * FROM t_news_state")
    fun select(): Flow<List<NewsState?>>
}
