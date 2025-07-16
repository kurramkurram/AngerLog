package io.github.kurramkurram.angerlog.data.repository

import android.content.Context
import io.github.kurramkurram.angerlog.data.datastore.KEY_UNREAD_TIPS
import io.github.kurramkurram.angerlog.data.datastore.getFromDataStore
import io.github.kurramkurram.angerlog.data.datastore.saveToDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Tipsのバージョン
 * - 1: 初版
 * - 2: 5thリリース向け更新
 */
private const val TIPS_VERSION = 2

/**
 * Tips画面の表示状態を管理するRepository.
 */
abstract class TipsRepository {
    /**
     * 未読のTipsがあるか.
     *
     * @param context [Context]
     */
    abstract fun isUnreadTipsExist(context: Context): Flow<Boolean>

    /**
     * 未読だったTipsを表示した.
     *
     * @param context [Context]
     */
    abstract suspend fun hasShowUnreadTips(context: Context)
}

/**
 * Tips画面の表示状態を管理するRepository.
 */
class TipsRepositoryImpl : TipsRepository() {
    /**
     * 未読のTipsがあるか.
     *
     * @param context [Context]
     */
    override fun isUnreadTipsExist(context: Context): Flow<Boolean> =
        getFromDataStore(context, KEY_UNREAD_TIPS, 0).map {
            it != TIPS_VERSION
        }

    /**
     * 未読だったTipsを表示した.
     *
     * @param context [Context]
     */
    override suspend fun hasShowUnreadTips(context: Context) {
        saveToDataStore(context, KEY_UNREAD_TIPS, TIPS_VERSION)
    }
}