package io.github.kurramkurram.angerlog.ui.screen.aboutapp

import io.github.kurramkurram.angerlog.ui.screen.initial.InitialDetailsDto
import io.github.kurramkurram.angerlog.ui.screen.initial.InitialViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.kurramkurram.angerlog.R
import io.github.kurramkurram.angerlog.ui.component.layout.AngerLogBackButtonLayout
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object AboutApp

/**
 * アプリについて画面.
 * 初期画面から利用規約・ボタンを削除
 *
 * @param modifier [Modifier]
 * @param onClickBack 戻る押下時の動作
 * @param viewModel 初期画面のViewModel
 */
@Composable
fun AboutAppScreen(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
    viewModel: InitialViewModel = koinViewModel(),
) {
    AngerLogBackButtonLayout(
        onClickBack = onClickBack,
        title = stringResource(R.string.about_app, stringResource(R.string.app_name))
    ) {
        Column(
            modifier =
            modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = modifier.size(50.dp),
                painter = painterResource(R.drawable.app_icon),
                contentDescription = stringResource(R.string.initial_app_icon_cd),
            )

            Spacer(Modifier.height(10.dp))

            Text(
                text = stringResource(R.string.initial_title, stringResource(R.string.app_name)),
                style = MaterialTheme.typography.titleLarge,
            )

            Spacer(Modifier.height(5.dp))

            Text(
                text = stringResource(R.string.initial_description),
                style = MaterialTheme.typography.titleSmall,
            )

            Spacer(Modifier.height(20.dp))

            Column(horizontalAlignment = Alignment.Start) {
                viewModel.getDetails().forEach {
                    AboutAppScreenIconListItem(
                        modifier = modifier.padding(vertical = 5.dp),
                        initialDetails = it,
                    )
                }
            }
        }
    }
}

/**
 * 初期画面のアイコン付きリストの項目
 *
 * @param modifier [Modifier]
 * @param initialDetails 説明のコンテンツ
 */
@Composable
fun AboutAppScreenIconListItem(
    modifier: Modifier = Modifier,
    initialDetails: InitialDetailsDto,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            modifier = modifier.padding(horizontal = 5.dp),
            imageVector = initialDetails.imageVector,
            contentDescription = initialDetails.description,
        )

        Text(modifier = modifier, text = initialDetails.description)
    }
}
