package io.github.kurramkurram.angerlog.ui.screen.license

/**
 * ライブラリのメタデータ.
 *
 * @param name ライブラリ名
 * @param offset third_party_licensesのオフセット
 * @param length third_party_licensesの長さ
 */
data class LibraryMetaDto(
    val name: String,
    val offset: Int,
    val length: Int,
)
