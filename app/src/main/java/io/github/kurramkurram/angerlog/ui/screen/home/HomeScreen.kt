package io.github.kurramkurram.angerlog.ui.screen.home

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Reviews
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.kurramkurram.angerlog.R
import io.github.kurramkurram.angerlog.ui.AngerLevel
import io.github.kurramkurram.angerlog.ui.component.dialog.AngerLogBasicDialog
import io.github.kurramkurram.angerlog.ui.component.layout.AngerLogTabLayout
import io.github.kurramkurram.angerlog.ui.component.layout.TabContent
import io.github.kurramkurram.angerlog.ui.component.text.AngerLogErrorText
import io.github.kurramkurram.angerlog.ui.component.text.AngerLogLinkText
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object Home

/**
 * ホーム画面.
 *
 * @param modifier [Modifier]
 * @param onItemClick リストの項目を押下した時の動作
 * @param onPolicyClick 利用規約を押下した時の動作
 * @param viewModel ホーム画面のViewModel
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onItemClick: (id: Long) -> Unit,
    onPolicyClick: () -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {
    Column(
        modifier =
            modifier
                .padding(top = 20.dp)
                .fillMaxSize(),
    ) {
        val state by viewModel.state.collectAsStateWithLifecycle()
        val context = LocalContext.current
        val activity = LocalActivity.current

        LaunchedEffect(Unit) {
            viewModel.checkShowPolicyDialog(context)
        }

        if (viewModel.showNewPolicyDialog) {
            AngerLogBasicDialog(
                title = stringResource(R.string.home_policy_dialog_title),
                descriptionContent = {
                    AngerLogLinkText(
                        modifier = modifier.padding(10.dp),
                        preText =
                            stringResource(
                                R.string.home_policy_pre_text,
                                stringResource(R.string.home_policy_confirm_button_text),
                            ),
                        linkText = stringResource(R.string.home_policy_link_text),
                        link = "",
                        onClickLink = { onPolicyClick() },
                        suffixText = stringResource(R.string.home_policy_suffix_text),
                    )
                },
                confirmText = stringResource(R.string.home_policy_confirm_button_text),
                dismissText = stringResource(R.string.home_policy_dismiss_button_text),
                onDismissRequest = {},
                onDismissClick = { activity?.finishAndRemoveTask() },
            ) { viewModel.agreePolicy(context) }
        }

        when (state) {
            is HomeUiState.Success -> {
                val success = state as HomeUiState.Success
                HomeScreenContent(
                    modifier = modifier,
                    state = success,
                ) { onItemClick(it) }
            }

            is HomeUiState.Loading -> {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = modifier.size(50.dp))
                }
            }

            is HomeUiState.Error -> AngerLogErrorText()
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
            modifier.fillMaxHeight(),
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
    Column {
        Text(
            modifier =
                modifier
                    .fillMaxWidth()
                    .padding(10.dp),
            text = stringResource(R.string.home_look_back_description),
            textAlign = TextAlign.Center,
        )

        LazyColumn(
            modifier =
                modifier
                    .fillMaxHeight(),
        ) {
            items(state.lookBackList) { angerLog ->
                HomeListItem(item = angerLog, onItemClick = { id -> onClick(id) })
            }
        }
    }
}

/**
 * ホーム画面のリストの項目.
 *
 * @param modifier [Modifier]
 * @param item リストの表示内容
 * @param onItemClick リストを選択した時の動作
 * @param isRoundTop 項目の上に丸みを付けるかどうか
 * @param isRoundBottom 項目の下に丸みを付けるかどうか
 */
@Composable
fun HomeListItem(
    modifier: Modifier = Modifier,
    item: HomeAngerLog,
    onItemClick: (id: Long) -> Unit,
    isRoundTop: Boolean = true,
    isRoundBottom: Boolean = true,
) {
    ElevatedCard(
        modifier =
            modifier
                .padding(10.dp)
                .clickable { onItemClick(item.getId()) },
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 6.dp,
            ),
    ) {
        Column {
            val shape =
                if (isRoundTop && isRoundBottom) {
                    MaterialTheme.shapes.small
                } else if (isRoundTop) {
                    RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                } else if (isRoundBottom) {
                    RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                } else {
                    RoundedCornerShape(0.dp)
                }
            Row(
                modifier =
                    modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.onPrimary, shape = shape)
                        .padding(horizontal = 15.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = modifier.weight(1f)) {
                    Text(text = item.getDate())
                    Text(text = item.getEvent(), maxLines = 1, overflow = TextOverflow.Ellipsis)
                }

                if (item.canShowLookBack()) {
                    Icon(
                        modifier = modifier.padding(horizontal = 20.dp),
                        imageVector = Icons.Outlined.Reviews,
                        contentDescription = stringResource(R.string.home_review_cd),
                    )
                }

                Text(
                    modifier =
                        modifier
                            .clip(CircleShape)
                            .background(
                                color = AngerLevel().select(level = item.getLevel()).getColor(),
                            )
                            .padding(horizontal = 20.dp, vertical = 5.dp),
                    text = "${item.getLevel()}",
                )
            }
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
