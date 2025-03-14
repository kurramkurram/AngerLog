package io.github.kurramkurram.angerlog.ui.screen

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.kurramkurram.angerlog.R
import io.github.kurramkurram.angerlog.util.requestPermission
import kotlinx.serialization.Serializable

private const val TAG = "PermissionScreen"

@Serializable
object Permission

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun PermissionScreen(
    modifier: Modifier = Modifier,
    onPermissionGranted: () -> Unit,
    onClickSkip: () -> Unit,
) {
    Column {
        // 何か表示するのでColumnでネスト
        Column(
            modifier =
                modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(10.dp),
        ) {
            Text(
                modifier = modifier,
                text = stringResource(R.string.permission_description),
            )
        }

        val launcher =
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
            ) { onPermissionGranted() }

        Button(
            modifier =
                modifier
                    .fillMaxWidth()
                    .padding(10.dp),
            onClick = {
                launcher.launch(requestPermission)
            },
        ) { Text(stringResource(R.string.permission_next_button)) }

        Button(
            modifier =
                modifier
                    .fillMaxWidth()
                    .padding(10.dp),
            onClick = onClickSkip,
        ) { Text(stringResource(R.string.permission_skip_button)) }
    }
}
