package io.github.kurramkurram.angerlog.ui.component.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * ダイアログ.
 *
 * @param modifier [Modifier]
 * @param title タイトル
 * @param descriptionContent 説明
 * @param confirmText 確定ボタンの文言
 * @param dismissText 消去ボタンの文言
 * @param onDismissRequest ダイアログ外押下時の動作
 * @param onDismissClick 消去ボタン押下時の動作
 * @param onConfirmClick 確定ボタン押下時の動作
 */
@Composable
fun AngerLogBasicDialog(
    modifier: Modifier = Modifier,
    title: String = "",
    descriptionContent: @Composable () -> Unit,
    confirmText: String = "",
    dismissText: String = "",
    onDismissRequest: () -> Unit,
    onDismissClick: () -> Unit,
    onConfirmClick: () -> Unit,
) {
    AlertDialog(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.onPrimary,
        title = {
            Text(text = title)
        },
        text = {
            descriptionContent()
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmClick()
                },
            ) {
                Text(confirmText)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissClick()
                },
            ) {
                Text(dismissText)
            }
        },
    )
}
