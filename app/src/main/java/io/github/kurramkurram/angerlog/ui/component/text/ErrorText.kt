package io.github.kurramkurram.angerlog.ui.component.text

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.kurramkurram.angerlog.R

/**
 * エラー画面のテキスト.
 *
 * @param modifier [Modifier]
 */
@Composable
fun AngerLogErrorText(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 20.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = modifier.verticalScroll(rememberScrollState()),
            text = stringResource(R.string.error_text),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}