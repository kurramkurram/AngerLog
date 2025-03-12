package io.github.kurramkurram.angerlog.ui.screen.initial

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.kurramkurram.angerlog.R
import io.github.kurramkurram.angerlog.ui.component.text.AngerLogLinkText
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

private const val TAG = "InitialScreen"

@Serializable
object Initial

@Composable
fun InitialScreen(
    modifier: Modifier = Modifier,
    onClickLicense: () -> Unit,
    onClick: () -> Unit,
    viewModel: InitialViewModel = koinViewModel()
) {
    Column {
        // 何か表示するのでColumnでネスト
        Column(
            modifier = modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                modifier = modifier,
                text = stringResource(R.string.initial_description)
            )
        }

        AngerLogLinkText(
            modifier = modifier.padding(10.dp),
            preText = stringResource(R.string.initial_policy_pre_text),
            linkText = stringResource(R.string.initial_policy_link_text),
            link = "",
            onClickLink = onClickLicense,
            suffixText = stringResource(R.string.initial_policy_suffix_text)
        )
        Button(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp),
            onClick = onClick
        ) {
            Text(stringResource(R.string.initial_button_text))
            viewModel.agree(LocalContext.current)
        }
    }
}
