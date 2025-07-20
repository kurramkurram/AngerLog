package io.github.kurramkurram.angerlog.data.repository

import android.content.Context
import io.github.kurramkurram.angerlog.R
import io.github.kurramkurram.angerlog.data.dao.NewsStateDao
import io.github.kurramkurram.angerlog.data.database.NewsStateDatabase
import io.github.kurramkurram.angerlog.model.NewsItem
import io.github.kurramkurram.angerlog.model.NewsState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.util.Date

/**
 * お知らせのRepository.
 */
abstract class NewsRepository {

    /**
     * お知らせ一覧のリスト.
     *
     * @return お知らせ一覧
     */
    abstract val newsList: Flow<List<NewsItem>>

    /**
     * お知らせの既読を記録.
     *
     * @param newsId 既読に変更するお知らせのid
     */
    abstract suspend fun markAsRead(newsId: Int)

    /**
     * お知らせを取得.
     *
     * @param newsId 取得したいお知らせのid
     * @return お知らせ
     */
    abstract fun getNews(newsId: Int): NewsItem

    /**
     * 未読のお知らせがあるかどうか判定.
     *
     * @return true: 未読あり
     */
    abstract fun isUnreadNewsExist(): Flow<Boolean>
}

/**
 * お知らせのRepository.
 *
 * @param context [Context]
 * @param db お知らせデータベース.
 * @param newsDao お知らせデータベースインターフェース
 */
class NewsRepositoryImpl(
    context: Context,
    private val db: NewsStateDatabase = NewsStateDatabase.getDatabases(context),
    private val newsDao: NewsStateDao = db.newsStateDao(),
) : NewsRepository() {

    private val news = loadNews(context)

    private val allNewsFlow: MutableStateFlow<List<NewsItem>> = MutableStateFlow(news)
    override val newsList: Flow<List<NewsItem>> = combine(
        allNewsFlow,
        newsDao.select()
    ) { allNews, readNewsEntities ->
        val readIds = readNewsEntities.map { it?.newsId }.toSet()
        allNews.map { it.copy(isRead = it.newsId in readIds) }
    }

    /**
     * お知らせを読み込み.
     *
     * @param context [Context]
     * @return お知らせ一覧.
     */
    private fun loadNews(context: Context): List<NewsItem> {
        val resources = context.resources
        return listOf(
            NewsItem(
                newsId = 1,
                date = Date(1753974000),
                title = resources.getString(R.string.news_1_title),
                description = resources.getString(R.string.news_1_description),
            ),
        )
    }

    /**
     * お知らせの既読を記録.
     *
     * @param newsId 既読に変更するお知らせのid
     */
    override suspend fun markAsRead(newsId: Int) {
        newsDao.insert(newsState = NewsState(newsId = newsId))
    }

    /**
     * お知らせを取得.
     *
     * @param newsId 取得したいお知らせのid
     * @return お知らせ
     */
    override fun getNews(newsId: Int): NewsItem {
        val items = news.filter { it.newsId == newsId }
        if (items.isEmpty()) throw IllegalArgumentException()
        return items[0]
    }

    /**
     * 未読のお知らせがあるかどうか判定.
     *
     * @return true: 未読あり
     */
    override fun isUnreadNewsExist(): Flow<Boolean> = newsList.map { list ->
        list.any { !it.isRead }
    }
}