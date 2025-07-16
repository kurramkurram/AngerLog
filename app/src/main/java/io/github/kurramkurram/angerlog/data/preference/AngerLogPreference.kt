package io.github.kurramkurram.angerlog.data.preference

import android.content.Context
import androidx.core.content.edit

/**
 * プリファレンスのファイル名.
 */
private const val PREFERENCE_FILE_USER_SETTING = "user_setting"

/**
 * 利用規約の同意状態.
 */
const val KEY_POLICY_AGREEMENT = "key_policy_agreement"

/**
 * 利用規約の同意済みのバージョン.
 */
const val KEY_POLICY_AGREEMENT_VERSION = "key_policy_agreement_version"

/**
 * SharedPreferenceに保存する.
 *
 * @param context [Context]
 * @param key 保存するキー
 * @param value 保存する値
 */
fun saveToPreference(
    context: Context,
    key: String,
    value: Int,
) {
    val sharedPreferences =
        context.getSharedPreferences(PREFERENCE_FILE_USER_SETTING, Context.MODE_PRIVATE)
    sharedPreferences.edit { putInt(key, value) }
}

/**
 * SharedPreferenceに保存する.
 *
 * @param context [Context]
 * @param key 取得するキー
 * @param defaultValue 未保存の時のデフォルト値
 * @return 取得したい値.
 */
fun getFromPreference(
    context: Context,
    key: String,
    defaultValue: Int,
): Int {
    val sharedPreferences =
        context.getSharedPreferences(PREFERENCE_FILE_USER_SETTING, Context.MODE_PRIVATE)
    return sharedPreferences.getInt(key, defaultValue)
}
