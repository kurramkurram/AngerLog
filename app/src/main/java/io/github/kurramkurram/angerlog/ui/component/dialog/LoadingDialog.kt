package io.github.kurramkurram.angerlog.ui.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun LoadingDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier =
                modifier
                    .size(100.dp)
                    .background(White, shape = MaterialTheme.shapes.small),
        ) {
            CircularProgressIndicator()
        }
    }
}
