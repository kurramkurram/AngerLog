package io.github.kurramkurram.angerlog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.android.gms.ads.MobileAds
import io.github.kurramkurram.angerlog.model.StartApp
import io.github.kurramkurram.angerlog.model.StartAppType
import io.github.kurramkurram.angerlog.ui.AngerLevel
import io.github.kurramkurram.angerlog.ui.theme.AngerLogTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val EXTRA_START_APP_ANGER_LEVEL = "extra_start_app_anger_level"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val backgroundScope = CoroutineScope(Dispatchers.IO)
        backgroundScope.launch {
            // バックグラウンドスレッドでAdMobを初期化する.
            MobileAds.initialize(this@MainActivity) {}
        }

        val startAppType = intent.action?.let { StartAppType.getType(it) } ?: StartAppType.DEFAULT
        val angerLevel =
            AngerLevel().getAngerLevelType(intent.getIntExtra(EXTRA_START_APP_ANGER_LEVEL, 1))
        val startApp = StartApp(startAppType = startAppType, angerLevel = angerLevel)

        enableEdgeToEdge()
        setContent {
            AngerLogTheme {
                AngerLogApp(startApp = startApp)
            }
        }
    }
}
