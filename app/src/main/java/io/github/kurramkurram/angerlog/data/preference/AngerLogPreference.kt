package io.github.kurramkurram.angerlog.data.preference

import android.content.Context

const val KEY_POLICY_AGREEMENT = "key_policy_agreement"

fun saveToPreference(
    context: Context,
    key: String,
    value: Int,
) {
    val sharedPreferences = context.getSharedPreferences("user_setting", Context.MODE_PRIVATE)
    sharedPreferences.edit().putInt(key, value).apply()
}

fun getFromPreference(
    context: Context,
    key: String,
    defaultValue: Int,
): Int {
    val sharedPreferences = context.getSharedPreferences("user_setting", Context.MODE_PRIVATE)
    return sharedPreferences.getInt(key, defaultValue)
}
