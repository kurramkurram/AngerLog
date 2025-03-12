package io.github.kurramkurram.angerlog.ui.screen

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import io.github.kurramkurram.angerlog.R
import io.github.kurramkurram.angerlog.ui.component.layout.AngerLogBackButtonLayout
import kotlinx.serialization.Serializable

@Serializable
object Policy

@Composable
fun PolicyScreen(modifier: Modifier = Modifier, onClickBack: () -> Unit) {
    AngerLogBackButtonLayout(
        onClickBack = onClickBack,
        title = stringResource(R.string.policy)
    ) {
        AndroidView(
            factory = { WebView(it) },
            modifier = modifier.fillMaxSize(),
            update = { webView ->
                webView.webViewClient = WebViewClient()
                webView.loadUrl("file:///android_asset/app.html")
            },
        )
    }
}