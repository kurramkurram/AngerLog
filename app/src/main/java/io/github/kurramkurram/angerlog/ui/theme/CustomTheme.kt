package io.github.kurramkurram.angerlog.ui.theme

import androidx.compose.runtime.Composable

object CustomTheme {
    val colorScheme: CustomColors
        @Composable
        get() = LocalCustomColorScheme.current
}