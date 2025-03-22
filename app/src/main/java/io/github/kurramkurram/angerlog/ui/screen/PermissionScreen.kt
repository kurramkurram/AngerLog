package io.github.kurramkurram.angerlog.ui.screen

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.kurramkurram.angerlog.R
import io.github.kurramkurram.angerlog.util.requestPermission
import kotlinx.serialization.Serializable

private const val TAG = "PermissionScreen"

@Serializable
object Permission

/**
 * 権限取得画面.
 *
 * @param modifier [Modifier]
 * @param onPermissionGranted RuntimePermissionダイアログ閉じた後の動作
 * @param onClickSkip スキップする押下後の動作
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun PermissionScreen(
    modifier: Modifier = Modifier,
    onPermissionGranted: () -> Unit,
    onClickSkip: () -> Unit,
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
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = modifier.padding(vertical = 10.dp),
                text = stringResource(R.string.permission_description),
                style = MaterialTheme.typography.bodyLarge,
            )

            Text(
                text = stringResource(R.string.permission_description_notice),
                style = MaterialTheme.typography.bodySmall,
            )

            Spacer(Modifier.height(20.dp))

            Image(
                modifier = modifier.padding(horizontal = 20.dp),
                painter = painterResource(R.drawable.permission),
                contentDescription = stringResource(R.string.permission_screenshot_cd),
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
