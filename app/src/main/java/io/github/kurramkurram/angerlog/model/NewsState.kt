package io.github.kurramkurram.angerlog.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * お知らせの既読状態.
 * データベースにもこの構成のテーブルで保存される.
 *
 * @param newsId 既読になったnewsId
 * @param date 日付
 */
@Entity(tableName = "t_news_state")
class NewsState(
    @PrimaryKey val newsId: Int = 0,
    val date: Date = Date()
)