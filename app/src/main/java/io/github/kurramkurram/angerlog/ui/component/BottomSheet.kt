package io.github.kurramkurram.angerlog.ui.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * ボトムシート.
 *
 * @param modifier [Modifier]
 * @param onDismissRequest ボトムシート終了時の動作
 * @param sheetState ボトムシートの状態
 * @param content コンテンツ
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AngerLogModalBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    content: @Composable () -> Unit,
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
    ) { content() }
}
