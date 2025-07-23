package io.github.kurramkurram.angerlog.model

import android.content.Intent
import io.github.kurramkurram.angerlog.ui.AngerLevelType

/**
 * アプリ起動時のデータ.
 *
 * @param startAppType [StartAppType]
 * @param angerLevel 怒りの強さ
 */
data class StartApp(val startAppType: StartAppType, val angerLevel: AngerLevelType)

/**
 * 起動時のタイプ.
 *
 * @param action 起動時のアクション
 */
enum class StartAppType(val action: String) {
    DEFAULT(Intent.ACTION_MAIN),
    REGISTER("io.github.kurramkurram.angerlog.ACTION_REGISTER"),
    ;

    companion object {
        fun getType(value: String): StartAppType {
            entries.forEach {
                if (it.action == value) {
                    return it
                }
            }
            return DEFAULT
        }
    }
}
