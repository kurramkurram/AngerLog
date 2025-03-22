package io.github.kurramkurram.angerlog.ui.screen.setting

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.outlined.OpenInNew
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * 設定画面のアイコンタイプ.
 *
 * @param imageVector アイコン画像
 */
enum class SettingLeadingIconType(val imageVector: ImageVector) {
    NextScreen(Icons.AutoMirrored.Default.KeyboardArrowRight),
    OpenInNew(Icons.AutoMirrored.Outlined.OpenInNew),
}
