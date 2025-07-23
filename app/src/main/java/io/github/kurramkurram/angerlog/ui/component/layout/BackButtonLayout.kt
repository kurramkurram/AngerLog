package io.github.kurramkurram.angerlog.ui.component.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.kurramkurram.angerlog.ui.component.AngerLogHorizontalDivider

/**
 * バックボタン付きの画面レイアウト.
 *
 * @param modifier [Modifier]
 * @param onClickBack バックボタン押下時の動作
 * @param title タイトル
 * @param description 説明
 * @param trailingText 右端ボタンの文言
 * @param onTrailingClick 右端ボタンの押下時の動作
 * @param content コンテンツ
 */
@Composable
fun AngerLogBackButtonLayout(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
    title: String = "",
    titleStyle: TextStyle = MaterialTheme.typography.titleLarge,
    description: String = "",
    trailingText: String = "",
    onTrailingClick: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Column {
        Box(modifier = modifier.padding(10.dp)) {
            Icon(
                modifier =
                    modifier
                        .clip(CircleShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) { onClickBack() }
                        .background(color = MaterialTheme.colorScheme.onPrimary)
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "戻る",
            )

            Column {
                Text(
                    modifier =
                        modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                            .graphicsLayer { alpha = if (title.isNotEmpty()) 1f else 0f },
                    text = title,
                    textAlign = TextAlign.Center,
                    style = titleStyle,
                )

                if (description.isNotEmpty()) {
                    Text(
                        modifier =
                            modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                        text = description,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                } else {
                    Spacer(modifier = modifier.height(5.dp))
                }

                AngerLogHorizontalDivider(
                    modifier = modifier,
                    color = MaterialTheme.colorScheme.primaryContainer,
                )
            }

            if (trailingText.isNotEmpty()) {
                Row(
                    Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    Text(text = trailingText, modifier = Modifier.clickable { onTrailingClick() })
                }
            }
        }
        content()
    }
}
