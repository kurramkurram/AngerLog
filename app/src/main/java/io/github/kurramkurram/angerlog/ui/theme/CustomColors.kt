package io.github.kurramkurram.angerlog.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * カスタムカラー.
 * Materialテーマに定義されていない独自の色を扱う.
 *
 * @param angerLevel1 怒りの強さ1
 * @param angerLevel2 怒りの強さ2
 * @param angerLevel3 怒りの強さ3
 * @param angerLevel4 怒りの強さ4
 * @param angerLevel5 怒りの強さ5
 * @param sunday 日曜日の色
 * @param saturday 土曜日の色
 * @param weekDays 平日の色
 */
data class CustomColors(
    val angerLevel1: Color,
    val angerLevel2: Color,
    val angerLevel3: Color,
    val angerLevel4: Color,
    val angerLevel5: Color,
    val sunday: Color,
    val saturday: Color,
    val weekDays: Color,
)
