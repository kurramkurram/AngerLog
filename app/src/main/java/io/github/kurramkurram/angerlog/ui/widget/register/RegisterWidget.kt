package io.github.kurramkurram.angerlog.ui.widget.register

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.clickable
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.text.Text
import io.github.kurramkurram.angerlog.EXTRA_START_APP_ANGER_LEVEL
import io.github.kurramkurram.angerlog.MainActivity
import io.github.kurramkurram.angerlog.R
import io.github.kurramkurram.angerlog.model.StartAppType
import io.github.kurramkurram.angerlog.ui.AngerLevel
import io.github.kurramkurram.angerlog.ui.AngerLevelType

/**
 * 登録ウィジェット.
 *
 * @param modifier [GlanceModifier]
 */
@Composable
fun RegisterWidget(modifier: GlanceModifier = GlanceModifier) {

    Column(
        modifier = modifier.fillMaxSize().background(colorProvider = GlanceTheme.colors.background)
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                provider = ImageProvider(R.drawable.glance_whatshot),
                contentDescription = context.getString(R.string.register_widget_title),
            )
            Text(
                text = context.getString(R.string.register_widget_title),
                modifier = GlanceModifier.padding(10.dp),
            )
        }
        RegisterWidgetAngerLevel(modifier = modifier)
    }
}

/**
 * 登録画面の怒りの強さ登録項目.
 *
 * @param modifier [GlanceModifier]
 */
@Composable
fun RegisterWidgetAngerLevel(modifier: GlanceModifier = GlanceModifier) {
    Row(
        modifier.padding(vertical = 10.dp)
            .fillMaxWidth()
            .cornerRadius(8.dp)
            .background(colorProvider = GlanceTheme.colors.onPrimary),
        horizontalAlignment = Alignment.Horizontal.CenterHorizontally,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val context = LocalContext.current

        for ((index, level) in AngerLevelType.entries.withIndex()) {
            Column(
                modifier = modifier.clickable(
                    onClick = actionStartActivity(
                        Intent(context, MainActivity::class.java).apply {
                            action = StartAppType.REGISTER.action
                            putExtra(EXTRA_START_APP_ANGER_LEVEL, index + 1)
                        }
                    ))
                    .padding(horizontal = 3.dp)
            ) {
                Text(
                    modifier =
                        modifier
                            .cornerRadius(20.dp)
                            .background(color = AngerLevel().select(level).getColor())
                            .padding(horizontal = 15.dp, vertical = 5.dp),
                    text = "${index + 1}",
                )
            }
        }
    }
}