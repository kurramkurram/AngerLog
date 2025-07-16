package io.github.kurramkurram.angerlog.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Tipsの未續を管理するキー.
 */
val KEY_UNREAD_TIPS = intPreferencesKey("key_unread_tips")

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

/**
 * DataStoreへの保存.
 *
 * @param context [Context]
 * @param key DataStoreへ保存するキー
 * @param value 保存したい値
 */
suspend fun saveToDataStore(
    context: Context,
    key: Preferences.Key<Boolean>,
    value: Boolean
) = context.dataStore.edit {
    it[key] = value
}

/**
 * DataStoreへの保存.
 *
 * @param context [Context]
 * @param key DataStoreへ保存するキー
 * @param value 保存したい値
 */
suspend fun saveToDataStore(
    context: Context,
    key: Preferences.Key<Int>,
    value: Int
) = context.dataStore.edit {
    it[key] = value
}

/**
 * DataStoreからの取得.
 *
 * @param context [Context]
 * @param key DataStoreから取得する値のキー
 * @param defValue デフォルト値
 */
fun getFromDataStore(
    context: Context,
    key: Preferences.Key<Boolean>,
    defValue: Boolean
): Flow<Boolean> =
    context.dataStore.data.map {
        it[key] ?: defValue
    }

/**
 * DataStoreからの取得.
 *
 * @param context [Context]
 * @param key DataStoreから取得する値のキー
 * @param defValue デフォルト値
 */
fun getFromDataStore(
    context: Context,
    key: Preferences.Key<Int>,
    defValue: Int
): Flow<Int> =
    context.dataStore.data.map {
        it[key] ?: defValue
    }

/**
 * DataStoreからの削除.
 *
 * @param context [Context]
 * @param key DataStoreから削除する値のキー
 */
suspend fun resetFromDataStore(
    context: Context,
    key: Preferences.Key<Boolean>
) = context.dataStore.edit {
    it.remove(key)
}
