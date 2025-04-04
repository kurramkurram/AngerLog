package io.github.kurramkurram.angerlog.ui.component

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * 水平方向のDivider.
 *
 * @param modifier [Modifier]
 * @param color Dividerの色
 */
@Composable
fun AngerLogHorizontalDivider(
    modifier: Modifier = Modifier,
    color: Color = Color.Gray,
) {
    HorizontalDivider(modifier = modifier, thickness = 1.dp, color = color)
}
