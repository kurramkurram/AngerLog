package io.github.kurramkurram.angerlog.ui.screen.initial

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * 初期画面の説明のコンテンツ.
 *
 * @param imageVector アイコン画像
 * @param description 説明
 */
data class InitialDetailsDto(
    val imageVector: ImageVector,
    val description: String,
)