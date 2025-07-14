package io.github.kurramkurram.angerlog.ui.screen.tips

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.kurramkurram.angerlog.R
import io.github.kurramkurram.angerlog.ui.component.card.AngerLogExpandableCard
import io.github.kurramkurram.angerlog.ui.component.layout.AngerLogBackButtonLayout
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object Tips

/**
 * お役立ちTips画面.
 *
 * @param modifier [Modifier]
 * @param onClickBack 戻る押下時の動作
 * @param viewModel お役立ちTips画面のViewModel
 */
@Composable
fun TipsScreen(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
    viewModel: TipsInfoViewModel = koinViewModel(),
) {
    AngerLogBackButtonLayout(
        modifier = modifier,
        onClickBack = onClickBack,
        title = stringResource(R.string.tips),
        description = stringResource(R.string.tips_description),
    ) {
        val data = viewModel.getTips()
        LazyColumn(
            modifier =
                modifier
                    .fillMaxHeight()
                    .padding(horizontal = 10.dp),
        ) {
            for (tipsInfoCategory in data) {
                stickyHeader {
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = modifier.padding(end = 10.dp),
                            imageVector = tipsInfoCategory.icon,
                            contentDescription = tipsInfoCategory.category
                        )
                        Text(
                            text = tipsInfoCategory.category,
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                }

                item {
                    Column(
                        modifier =
                            modifier.background(
                                color = MaterialTheme.colorScheme.onPrimary,
                                shape = MaterialTheme.shapes.medium,
                            ),
                    ) {
                        tipsInfoCategory.info.forEachIndexed { index, tipsInfo ->
                            val isLast = index == tipsInfoCategory.info.size - 1
                            AngerLogExpandableCard(
                                modifier = modifier,
                                title = tipsInfo.title,
                                content = tipsInfo.content,
                                isBottomRound = isLast,
                            )
                        }
                    }
                }
            }

            item {
                Text(
                    modifier = modifier.padding(10.dp),
                    text = stringResource(R.string.tips_attention)
                )
            }
        }
    }
}
