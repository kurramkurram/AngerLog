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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.kurramkurram.angerlog.R
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
                if (success.hasAngerLog) {
                    LazyColumn(
                        modifier =
                            modifier
                                .padding(10.dp)
                                .fillMaxHeight(),
                    ) {
                        items(success.angerLogList) { angerLog ->
                            HomeListItem(item = angerLog, onItemClick = { id -> onClick(id) })
                        }
                    }
                } else {
                    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = stringResource(R.string.home_no_anger_log),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }
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

// @Composable
// fun HomeListItem(
//    modifier: Modifier = Modifier,
//    homeAngerLog: HomeAngerLogDto,
//    onClick: (id: Long) -> Unit,
// ) {
//    ElevatedCard(
//        modifier =
//        modifier
//            .padding(10.dp)
//            .clickable { onClick(homeAngerLog.getId()) },
//        elevation =
//        CardDefaults.cardElevation(
//            defaultElevation = 6.dp,
//        ),
//    ) {
//        HomeListItem(
//            modifier = modifier,
//            item = homeAngerLog,
//            onItemClick = { onClick(it) })
//    }
// }

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
