package io.github.kurramkurram.angerlog.ui.screen.news

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Badge
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.kurramkurram.angerlog.R
import io.github.kurramkurram.angerlog.model.NewsItem
import io.github.kurramkurram.angerlog.ui.component.AngerLogHorizontalDivider
import io.github.kurramkurram.angerlog.ui.component.layout.AngerLogBackButtonLayout
import io.github.kurramkurram.angerlog.util.DateConverter.Companion.dateToString
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
object News

/**
 * お知らせ画面.
 *
 * @param modifier [Modifier]
 * @param onClickBack 戻る押下時の動作
 * @param onItemClick リストのアイテム押下時の動作
 * @param viewModel お知らせのViewModel
 */
@Composable
fun NewsScreen(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
    onItemClick: (Int) -> Unit,
    viewModel: NewsViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    if (state is NewsUiState.Success) {
        NewsScreenContent(
            modifier = modifier,
            state = state as NewsUiState.Success,
            onClickBack = onClickBack,
            onItemClick = onItemClick,
        )
    } else {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(modifier = modifier.size(50.dp))
        }
    }
}

/**
 * お知らせ画面のデータ取得成功時の画面.
 *
 * @param modifier [Modifier]
 * @param onClickBack 戻る押下時の動作
 * @param onItemClick リスト選択時の動作
 * @param state お知らせ画面の状態
 */
@Composable
fun NewsScreenContent(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
    onItemClick: (Int) -> Unit,
    state: NewsUiState.Success,
) {
    val newsList = state.newsList
    AngerLogBackButtonLayout(
        onClickBack = onClickBack,
        title = stringResource(R.string.news),
    ) {
        LazyColumn {
            items(newsList) { news ->
                NewsScreenListItem(modifier = modifier, news = news) { onItemClick(it) }
                AngerLogHorizontalDivider()
            }
        }
    }
}

/**
 * お知らせリスト項目.
 *
 * @param modifier [Modifier]
 * @param news お知らせ
 * @param onItemClick 項目選択時の動作
 */
@Composable
fun NewsScreenListItem(
    modifier: Modifier = Modifier,
    news: NewsItem,
    onItemClick: (Int) -> Unit,
) {
    Row(
        modifier =
            modifier
                .background(
                    color = Color.Transparent,
                    shape = MaterialTheme.shapes.medium,
                )
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ) {
                    onItemClick(news.newsId)
                }
                .padding(horizontal = 10.dp, vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier =
                modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp),
        ) {
            Text(text = news.title, style = MaterialTheme.typography.titleMedium)

            Text(text = news.date.dateToString(), style = MaterialTheme.typography.bodySmall)
        }

        if (!news.isRead) {
            Badge(modifier = modifier.padding(vertical = 10.dp))
        }

        Icon(
            imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
            contentDescription = stringResource(R.string.news_next_cd),
        )
    }
}
