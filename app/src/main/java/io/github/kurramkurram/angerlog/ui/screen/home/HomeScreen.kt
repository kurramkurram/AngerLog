package io.github.kurramkurram.angerlog.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.kurramkurram.angerlog.R
import io.github.kurramkurram.angerlog.ui.component.layout.AngerLogTabLayout
import io.github.kurramkurram.angerlog.ui.component.layout.TabContent
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object Home

/**
 * ホーム画面.
 *
 * @param modifier [Modifier]
 * @param onClick リストの項目を押下した時の動作
 * @param viewModel ホーム画面のViewModel
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onClick: (id: Long) -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {
    Column(
        modifier =
            modifier
                .padding(top = 20.dp)
                .fillMaxSize(),
    ) {
        val state by viewModel.state.collectAsStateWithLifecycle()

        Text(
            modifier =
                modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
            text = stringResource(R.string.home_title),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
        )

        when (state) {
            is HomeUiState.Success -> {
                val success = state as HomeUiState.Success
                HomeScreenContent(
                    modifier = modifier,
                    state = success,
                ) { onClick(it) }
            }

            is HomeUiState.Loading -> {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = modifier.size(50.dp))
                }
            }

            is HomeUiState.Error -> {}
        }
    }
}

/**
 * ホーム画面のデータ取得成功時の画面.
 *
 * @param modifier [Modifier]
 * @param state ホーム画面の状態
 * @param onClick リスト選択時の動作
 */
@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    state: HomeUiState.Success,
    onClick: (Long) -> Unit,
) {
    val list =
        mutableListOf(
            TabContent(
                label = stringResource(R.string.home_tab_anger),
            ) {
                HomeScreenAngerContent(
                    modifier = Modifier,
                    state = state,
                    onClick = { onClick(it) },
                )
            },
        )
    if (state.hasLookBack) {
        list.add(
            TabContent(
                label = stringResource(R.string.home_tab_look_back),
            ) {
                HomeScreenLookBackContent(
                    modifier = Modifier,
                    state = state,
                    onClick = { onClick(it) },
                )
            },
        )
    }

    val pagerState = rememberPagerState(initialPage = 0) { list.size }
    val scope = rememberCoroutineScope()
    AngerLogTabLayout(
        modifier = modifier,
        pagerState = pagerState,
        list = list,
        selectedIndex = pagerState.currentPage,
        onTabSelected = {
            scope.launch {
                pagerState.animateScrollToPage(it)
            }
        },
    )
}

/**
 * ホームの「最近の怒り」のタブの内容.
 *
 * @param modifier [Modifier]
 * @param state ホーム画面の状態
 * @param onClick リスト選択時の動作
 */
@Composable
fun HomeScreenAngerContent(
    modifier: Modifier = Modifier,
    state: HomeUiState.Success,
    onClick: (Long) -> Unit,
) {
    if (state.hasAngerLog) {
        HomeScreenAngerContentHasRecord(modifier = modifier, state = state, onClick = onClick)
    } else {
        HomeScreenAngerContentNoRecord(modifier = modifier)
    }
}

/**
 * ホームの「最近の怒り」のタブにアイテムが存在するときの画面.
 *
 * @param modifier [Modifier]
 * @param state ホーム画面の状態
 * @param onClick リスト選択時の動作
 */
@Composable
fun HomeScreenAngerContentHasRecord(
    modifier: Modifier = Modifier,
    state: HomeUiState.Success,
    onClick: (Long) -> Unit,
) {
    LazyColumn(
        modifier =
            modifier
                .padding(10.dp)
                .fillMaxHeight(),
    ) {
        items(state.angerLogList) { angerLog ->
            HomeListItem(item = angerLog, onItemClick = { id -> onClick(id) })
        }
    }
}

/**
 * ホームの「最近の怒り」のタブにアイテムが存在しないときの画面.
 *
 * @param modifier [Modifier]
 */
@Composable
fun HomeScreenAngerContentNoRecord(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(R.string.home_no_anger_log),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

/**
 * ホームの「振り返り」のタブにアイテムが存在するときの画面.
 *
 * @param modifier [Modifier]
 * @param state ホーム画面の状態
 * @param onClick リスト選択時の動作
 */
@Composable
fun HomeScreenLookBackContent(
    modifier: Modifier = Modifier,
    state: HomeUiState.Success,
    onClick: (Long) -> Unit,
) {
    LazyColumn(
        modifier =
            modifier
                .padding(10.dp)
                .fillMaxHeight(),
    ) {
        items(state.lookBackList) { angerLog ->
            HomeListItem(item = angerLog, onItemClick = { id -> onClick(id) })
        }
    }
}

//
// @Preview
// @Composable
// fun PreviewHomeScreen() {
//    HomeScreen {  }
// }

// @Composable
// @Preview
// fun PreviewHomeListItem() {
//    val log = AngerLog(
//        id = 0,
//        date = "2025/01/16",
//        time = "23:08",
//        level = 5,
//        place = "駅",
//        event = "AAAAA",
//        thought = "aaaaa",
//    )
//    HomeListItem(angerLog = log, onClick = {})
// }
