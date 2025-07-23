package io.github.kurramkurram.angerlog.ui.screen.newsdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.kurramkurram.angerlog.ui.component.layout.AngerLogBackButtonLayout
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
data class NewsDetail(val newsId: Int)

/**
 * お知らせ詳細画面.
 *
 * @param modifier [Modifier]
 * @param newsId お知らせ一覧で選択されたお知らせのid
 * @param onClickBack 戻る押下時の動作
 * @param viewModel お知らせ詳細のViewModel
 */
@Composable
fun NewsDetailScreen(
    modifier: Modifier = Modifier,
    newsId: Int,
    onClickBack: () -> Unit,
    viewModel: NewsDetailViewModel = koinViewModel(),
) {
    LaunchedEffect(Unit) { viewModel.initialize(newsId = newsId) }

    val state by viewModel.state.collectAsStateWithLifecycle()
    if (state is NewsDetailUiState.Success) {
        val success = state as NewsDetailUiState.Success
        NewsDetailScreenContent(modifier = modifier, state = success, onClickBack = onClickBack)
    } else {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(modifier = modifier.size(50.dp))
        }
    }
}

/**
 * お知らせ詳細画面のデータ取得成功時の画面.
 *
 * @param modifier [Modifier]
 * @param state お知らせ詳細画面の状態
 * @param onClickBack 戻る押下時の動作
 */
@Composable
fun NewsDetailScreenContent(
    modifier: Modifier = Modifier,
    state: NewsDetailUiState.Success,
    onClickBack: () -> Unit,
) {
    AngerLogBackButtonLayout(
        onClickBack = onClickBack,
        title = state.title,
        titleStyle = MaterialTheme.typography.titleSmall
    ) {
        Text(
            modifier =
                modifier
                    .padding(horizontal = 10.dp)
                    .verticalScroll(rememberScrollState()),
            text = state.description,
        )
    }
}
