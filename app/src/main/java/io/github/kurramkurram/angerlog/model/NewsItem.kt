package io.github.kurramkurram.angerlog.model

import java.util.Date

/**
 * お知らせの内容.
 *
 * @param newsId お知らせのid
 * @param date 日付
 * @param title タイトル
 * @param description 説明
 * @param isRead 既読状態
 */
data class NewsItem(
    val newsId: Int,
    val date: Date,
    val title: String,
    val description: String,
    val isRead: Boolean = false,
)
