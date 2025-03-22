package io.github.kurramkurram.angerlog.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * テキストボックス（外枠付き）.
 * https://developer.android.com/reference/kotlin/androidx/compose/foundation/text/package-summary#BasicTextField(androidx.compose.foundation.text.input.TextFieldState,androidx.compose.ui.Modifier,kotlin.Boolean,kotlin.Boolean,androidx.compose.foundation.text.input.InputTransformation,androidx.compose.ui.text.TextStyle,androidx.compose.foundation.text.KeyboardOptions,androidx.compose.foundation.text.input.KeyboardActionHandler,androidx.compose.foundation.text.input.TextFieldLineLimits,kotlin.Function2,androidx.compose.foundation.interaction.MutableInteractionSource,androidx.compose.ui.graphics.Brush,androidx.compose.foundation.text.input.OutputTransformation,androidx.compose.foundation.text.input.TextFieldDecorator,androidx.compose.foundation.ScrollState)
 *
 * @param modifier [Modifier]
 * @param value テキストボックスに表示する文言
 * @param onValueChange テキストボックス内が変更されたときの動作
 * @param hint ヒント
 * @param enabled テキストボックスの有効状態
 * @param readOnly 書き込み禁止状態
 * @param textStyle テキストボックス内の文言の書式
 * @param keyboardOptions キーボード構成オプション
 * @param keyboardActions キーボードアクション
 * @param visualTransformation 入力文字列の出力
 * @param onTextLayout テキストボックスが入力可能になったときに実行されるコールバック
 * @param interactionSource ユーザーの操作を受け取る
 * @param cursorBrush テキストボックスのカーソルの色
 * @param singleLine 1行で収めるかどうか
 * @param height テキストボックスの高さ
 */
@Composable
fun AngerLogOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = "",
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = TextStyle.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    cursorBrush: Brush = SolidColor(Color.Black),
    singleLine: Boolean = false,
    height: Dp = Dp.Unspecified,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier =
            modifier
                .border(
                    border = BorderStroke(1.dp, Color.DarkGray),
                    shape = MaterialTheme.shapes.small,
                )
                .background(
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = MaterialTheme.shapes.small,
                )
                .padding(),
        enabled = enabled,
        readOnly = readOnly,
        textStyle =
            textStyle.copy(
                color = MaterialTheme.colorScheme.onSurface,
            ),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        onTextLayout = onTextLayout,
        interactionSource = interactionSource,
        cursorBrush = cursorBrush,
        singleLine = singleLine,
        decorationBox = { innerTextField ->

            Row(
                modifier =
                    if (height != Dp.Unspecified) {
                        modifier.height(height)
                    } else {
                        modifier
                    },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    Modifier
                        .weight(1f)
                        .padding(10.dp)
                        .align(Alignment.Top),
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = hint,
                            style =
                                LocalTextStyle.current.copy(
                                    color = Color.Gray,
                                ),
                        )
                    }
                    innerTextField()
                }
            }
        },
    )
}
