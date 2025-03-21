package io.github.kurramkurram.angerlog.ui.component.ad

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import io.github.kurramkurram.angerlog.R

@Composable
fun AngerLogBannerAd(modifier: Modifier = Modifier) {
    Box(modifier.height(50.dp)) {
        AndroidView(modifier = modifier.fillMaxWidth(), factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = context.resources.getString(R.string.ad_unit_id)
                loadAd(AdRequest.Builder().build())
            }
        })
    }
}
