package io.github.kurramkurram.angerlog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.android.gms.ads.MobileAds
import io.github.kurramkurram.angerlog.ui.theme.AngerLogTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val backgroundScope = CoroutineScope(Dispatchers.IO)
        backgroundScope.launch {
            // バックグラウンドスレッドでAdMobを初期化する.
            MobileAds.initialize(this@MainActivity) {}
        }
        enableEdgeToEdge()
        setContent {
            AngerLogTheme {
                AngerLogApp()
            }
        }
    }
}
