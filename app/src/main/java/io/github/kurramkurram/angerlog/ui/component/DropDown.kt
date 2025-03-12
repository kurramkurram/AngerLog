package io.github.kurramkurram.angerlog.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * ドロップダウン.
 */
@Composable
fun AngerLogDropDown(
    modifier: Modifier = Modifier,
    min: Int,
    max: Int,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onItemSelected: (Int) -> Unit
) {
    DropdownMenu(
        modifier = modifier
            .width(200.dp)
            .border(
                border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primaryContainer),
                shape = MaterialTheme.shapes.small
            )
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = MaterialTheme.shapes.small
            )
            .padding(4.dp),
        expanded = expanded,
        onDismissRequest = { onDismissRequest() }
    ) {
        (min..max).forEach {
            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable {
                        onItemSelected(it)
                        onDismissRequest()
                    }
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                text = "$it",
                textAlign = TextAlign.Center
            )

            AngerLogHorizontalDivider()
        }
    }
}