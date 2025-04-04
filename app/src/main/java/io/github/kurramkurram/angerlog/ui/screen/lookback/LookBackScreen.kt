package io.github.kurramkurram.angerlog.ui.screen.lookback

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material.icons.outlined.Reviews
import androidx.compose.material.icons.sharp.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.kurramkurram.angerlog.R
import io.github.kurramkurram.angerlog.ui.AngerLevel
import io.github.kurramkurram.angerlog.ui.AngerLevelType
import io.github.kurramkurram.angerlog.ui.component.AngerLogHorizontalDivider
import io.github.kurramkurram.angerlog.ui.component.AngerLogOutlinedTextField

/**
 * 振り返り画面.
 *
 * @param modifier [Modifier]
 * @param onClickClose 閉じる押下時の動作
 * @param selectedAngerLevel 選択された振り返りの怒りの強さ
 * @param onSelectedAngerLevel 振り返りの怒りの強さ選択時の動作
 * @param whyAngerText 振り返りのなぜ怒りを感じたのかの文言
 * @param onWhyAngerChanged 振り返りのなぜ怒りを感じたのか変更時の動作
 * @param adviceText 振り返りのアドバイスの文言
 * @param onAdviceChanged 振り返りのアドバイス変更時の動作
 * @param onClickSave 保存する押下時の動作
 */
@Composable
fun LookBackScreen(
    modifier: Modifier = Modifier,
    onClickClose: () -> Unit,
    selectedAngerLevel: AngerLevelType?,
    onSelectedAngerLevel: (AngerLevelType) -> Unit,
    whyAngerText: String,
    onWhyAngerChanged: (String) -> Unit,
    adviceText: String,
    onAdviceChanged: (String) -> Unit,
    onClickSave: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 5.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier =
                modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = modifier.clickable { onClickClose() },
                text = stringResource(R.string.look_back_close),
            )
            Icon(
                modifier = modifier.weight(1f),
                imageVector = Icons.Outlined.Reviews,
                contentDescription = stringResource(R.string.look_back),
            )
            Text(
                modifier = modifier.clickable { onClickSave() },
                text = stringResource(R.string.look_back_save),
            )
        }

        AngerLogHorizontalDivider(
            modifier = modifier,
            color = MaterialTheme.colorScheme.primaryContainer,
        )

        Text(
            modifier = modifier.fillMaxWidth(),
            text = stringResource(R.string.look_back_description),
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
        )

        LookBackScreenItem(
            title = stringResource(R.string.look_back_anger_level_title),
            icon = {
                Icon(
                    imageVector = Icons.Filled.Whatshot,
                    contentDescription = stringResource(R.string.look_back_anger_level_title),
                )
            },
        ) { LookBackScreenAngerLevel(selected = selectedAngerLevel) { onSelectedAngerLevel(it) } }

        LookBackScreenItem(
            title = stringResource(R.string.look_back_why_anger_title),
            icon = {
                Icon(
                    imageVector = Icons.Filled.QuestionMark,
                    contentDescription = stringResource(R.string.look_back_why_anger_title),
                )
            },
        ) {
            AngerLogOutlinedTextField(
                value = whyAngerText,
                onValueChange = onWhyAngerChanged,
                hint = stringResource((R.string.look_back_why_anger_hint)),
                height = 100.dp,
            )
        }

        LookBackScreenItem(
            title = stringResource(R.string.look_back_advice_title),
            icon = {
                Icon(
                    imageVector = Icons.Filled.Lightbulb,
                    contentDescription = stringResource(R.string.look_back_advice_hint),
                )
            },
        ) {
            AngerLogOutlinedTextField(
                value = adviceText,
                onValueChange = onAdviceChanged,
                hint = stringResource((R.string.look_back_advice_hint)),
                height = 100.dp,
            )
        }

        Spacer(Modifier.height(10.dp))
    }
}

/**
 * 振り返り画面の項目.
 *
 * @param modifier [Modifier]
 * @param title タイトル
 * @param onClickAssist アシストクリックしたときの動作
 * @param icon アイコン
 * @param content コンテンツ
 */
@Composable
fun LookBackScreenItem(
    modifier: Modifier = Modifier,
    title: String = "",
    onClickAssist: (() -> Unit)? = null,
    icon: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    Column(modifier = modifier.padding(vertical = 10.dp)) {
        Row(
            modifier = modifier.padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            icon()

            Text(modifier = modifier.padding(horizontal = 10.dp), text = title)

            onClickAssist?.let {
                Image(
                    imageVector = Icons.Sharp.CheckCircle,
                    modifier = modifier.clickable { onClickAssist() },
                    contentDescription = "",
                )
            }
        }
        content()
    }
}

/**
 * 振り返り画面の怒りの強さ登録項目.
 *
 * @param modifier [Modifier]
 * @param selected 選択されている怒りの強さ
 * @param onSelected 怒りの強さ選択した時の動作
 */
@Composable
fun LookBackScreenAngerLevel(
    modifier: Modifier = Modifier,
    selected: AngerLevelType?,
    onSelected: (AngerLevelType) -> Unit,
) {
    Row(
        modifier
            .fillMaxWidth()
            .border(
                border = BorderStroke(1.dp, Color.DarkGray),
                shape = MaterialTheme.shapes.small,
            )
            .background(
                color = MaterialTheme.colorScheme.onPrimary,
                shape = MaterialTheme.shapes.small,
            )
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        for ((index, level) in AngerLevelType.entries.withIndex()) {
            Box(modifier = modifier.clickable { onSelected(level) }) {
                Text(
                    modifier =
                        modifier
                            .clip(CircleShape)
                            .border(
                                border =
                                    BorderStroke(
                                        width = 2.dp,
                                        color =
                                            if (selected == level) {
                                                MaterialTheme.colorScheme.primaryContainer
                                            } else {
                                                Color.Transparent
                                            },
                                    ),
                                shape = CircleShape,
                            )
                            .background(color = AngerLevel().select(level).getColor())
                            .padding(horizontal = 20.dp, vertical = 5.dp),
                    text = "${index + 1}",
                )
            }
        }
    }
}

//
// //@Preview
// //@Composable
// //fun PreviewLookBackScreen() {
// //    LookBackScreen(onClickButton = {}) { }
// //}
