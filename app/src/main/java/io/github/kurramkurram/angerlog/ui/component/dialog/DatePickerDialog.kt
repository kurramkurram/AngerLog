package io.github.kurramkurram.angerlog.ui.component.dialog

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * 日付選択ダイアログ.
 *
 * @param modifier [Modifier]
 * @param datePickerState 日付選択状態、選択中の日付の状態を保持する
 * @param onConfirmRequest 日付選択時の動作
 * @param onDismissRequest ダイアログ終了時の動作
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AngerLogDatePickerDialog(
    modifier: Modifier = Modifier,
    datePickerState: DatePickerState,
    onConfirmRequest: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
) {
    DatePickerDialog(
        modifier = modifier,
        colors = DatePickerDefaults.colors(containerColor = MaterialTheme.colorScheme.onPrimary),
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmRequest()
                    onDismissRequest()
                },
            ) {
                Text(text = "保存")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismissRequest() },
            ) {
                Text(text = "キャンセル")
            }
        },
    ) {
        DatePicker(datePickerState)
    }
}
