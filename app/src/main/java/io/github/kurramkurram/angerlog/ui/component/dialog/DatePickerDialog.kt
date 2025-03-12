package io.github.kurramkurram.angerlog.ui.component.dialog

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AngerLogDatePickerDialog(
    modifier: Modifier = Modifier,
    datePickerState: DatePickerState,
    onConfirmRequest: () -> Unit = {},
    onDismissRequest: () -> Unit = {}
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
                }
            ) {
                Text(text = "保存")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismissRequest() }
            ) {
                Text(text = "キャンセル")
            }
        }
    ) {
        DatePicker(datePickerState)
    }
}