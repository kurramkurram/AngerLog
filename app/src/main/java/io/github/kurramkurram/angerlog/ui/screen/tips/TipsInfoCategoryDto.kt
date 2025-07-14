package io.github.kurramkurram.angerlog.ui.screen.tips

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * お役立ちTipsのカテゴリ別のコンテンツ.
 *
 * @param icon アイコン
 * @param category コンテンツの属するカテゴリ
 * @param info コンテンツのリスト
 */
class TipsInfoCategoryDto(val icon: ImageVector, val category: String, val info: List<TipsInfoDto>)
