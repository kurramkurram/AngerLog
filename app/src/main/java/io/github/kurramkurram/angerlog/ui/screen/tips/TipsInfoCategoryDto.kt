package io.github.kurramkurram.angerlog.ui.screen.tips

/**
 * お役立ちTipsのカテゴリ別のコンテンツ.
 *
 * @param category コンテンツの属するカテゴリ
 * @param info コンテンツのリスト
 */
class TipsInfoCategoryDto(val category: String, val info: List<TipsInfoDto>)
