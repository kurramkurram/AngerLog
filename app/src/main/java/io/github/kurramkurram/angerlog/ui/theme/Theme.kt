package io.github.kurramkurram.angerlog.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

/**
 * Materialテーマのダークモードカラー上書き.
 */
val DarkColorScheme =
    darkColorScheme(
        primary = Blue60,
        onPrimary = Blue80,
        primaryContainer = Blue60,
        onPrimaryContainer = Gray10,
        secondary = Gray40,
        onSecondary = White100,
        secondaryContainer = Green40,
        onSecondaryContainer = Gray10,
        background = Blue90,
        onBackground = Gray10,
        surface = Blue80,
        onSurface = Gray10,
        error = Red60,
        onError = White100,
    )

/**
 * Materialテーマのライトモードカラー上書き.
 */
val LightColorScheme =
    lightColorScheme(
        primary = Blue40,
        onPrimary = White100,
        primaryContainer = Blue60,
        onPrimaryContainer = Gray90,
        secondary = Gray10,
        onSecondary = White100,
        secondaryContainer = Green60,
        onSecondaryContainer = Gray90,
        background = Blue10,
        onBackground = Gray90,
        surface = Blue20,
        onSurface = Gray90,
        error = Red80,
        onError = White100,
    )

/**
 * カスタムテーマのカスタムテーマを定義.
 */
val LocalCustomColorScheme =
    staticCompositionLocalOf {
        CustomColors(
            angerLevel1 = Color.Unspecified,
            angerLevel2 = Color.Unspecified,
            angerLevel3 = Color.Unspecified,
            angerLevel4 = Color.Unspecified,
            angerLevel5 = Color.Unspecified,
            sunday = Color.Unspecified,
            saturday = Color.Unspecified,
            weekDays = Color.Unspecified,
        )
    }

/**
 * カスタムテーマのライトモードカラー.
 */
val LightLocalCustomColors =
    CustomColors(
        angerLevel1 = Blue40,
        angerLevel2 = Green40,
        angerLevel3 = Yellow40,
        angerLevel4 = Orange40,
        angerLevel5 = Red40,
        sunday = Red80,
        saturday = Blue60,
        weekDays = Gray90,
    )

/**
 * カスタムテーマのダークモードカラー.
 */
val DarkLocalCustomColorScheme =
    CustomColors(
        angerLevel1 = Blue60,
        angerLevel2 = Green60,
        angerLevel3 = Yellow80,
        angerLevel4 = Orange80,
        angerLevel5 = Red60,
        sunday = Red60,
        saturday = Blue60,
        weekDays = White100,
    )

/**
 * アンガーログ向けのテーマ
 */
@Composable
fun AngerLogTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme =
        when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }

            darkTheme -> DarkColorScheme
            else -> LightColorScheme
        }

    val customColorScheme = if (darkTheme) DarkLocalCustomColorScheme else LightLocalCustomColors

    CompositionLocalProvider(LocalCustomColorScheme provides customColorScheme) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content,
        )
    }
}
