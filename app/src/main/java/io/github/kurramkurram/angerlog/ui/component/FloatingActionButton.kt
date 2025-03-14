package io.github.kurramkurram.angerlog.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AngerLogFloatingActionButton(
    modifier: Modifier = Modifier,
    title: String = "",
    onClick: () -> Unit,
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = { onClick() },
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(Icons.Filled.Edit, contentDescription = "編集")
            if (title.isNotBlank()) {
                Text(modifier = modifier.padding(horizontal = 5.dp), text = title)
            }
        }
    }
}
//
// @Preview
// @Composable
// fun PreviewAngerLogFloatingActionButton() {
//    AngerLogFloatingActionButton(onClick = {}, title = "振り返り")
// }
