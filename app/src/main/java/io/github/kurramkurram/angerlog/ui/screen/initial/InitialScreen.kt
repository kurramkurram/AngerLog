package io.github.kurramkurram.angerlog.ui.screen.initial

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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.kurramkurram.angerlog.R
import io.github.kurramkurram.angerlog.ui.component.text.AngerLogLinkText
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object Initial

@Composable
fun InitialScreen(
    modifier: Modifier = Modifier,
    onClickLicense: () -> Unit,
    onClick: () -> Unit,
    viewModel: InitialViewModel = koinViewModel(),
) {
    Column {
        Column(
            modifier =
                modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
                    .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(30.dp))

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
                    InitialScreenIconListItem(
                        modifier = modifier.padding(vertical = 5.dp),
                        imageVector = it.imageVector,
                        description = it.description,
                    )
                }
            }
        }

        AngerLogLinkText(
            modifier = modifier.padding(10.dp),
            preText = stringResource(R.string.initial_policy_pre_text),
            linkText = stringResource(R.string.initial_policy_link_text),
            link = "",
            onClickLink = onClickLicense,
            suffixText = stringResource(R.string.initial_policy_suffix_text),
        )

        val context = LocalContext.current
        Button(
            modifier =
                modifier
                    .fillMaxWidth()
                    .padding(10.dp),
            onClick = {
                onClick()
                viewModel.agree(context)
            },
        ) {
            Text(stringResource(R.string.initial_button_text))
        }
    }
}

@Composable
fun InitialScreenIconListItem(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    description: String,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            modifier = modifier.padding(horizontal = 5.dp),
            imageVector = imageVector,
            contentDescription = description,
        )

        Text(modifier = modifier, text = description)
    }
}
