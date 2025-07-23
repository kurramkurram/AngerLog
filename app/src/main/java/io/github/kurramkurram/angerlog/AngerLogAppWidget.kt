package io.github.kurramkurram.angerlog

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import io.github.kurramkurram.angerlog.ui.WidgetType
import io.github.kurramkurram.angerlog.ui.theme.AngerLogGlanceTheme
import io.github.kurramkurram.angerlog.ui.widget.register.RegisterWidget

/**
 * ウィジェット全体.
 *
 * @param widgetType ウィジェットタイプ
 */
class AngerLogAppWidget(private val widgetType: WidgetType = WidgetType.REGISTER) :
    GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            AngerLogGlanceTheme {
                if (widgetType == WidgetType.REGISTER) {
                    RegisterWidget()
                }
            }
        }
    }
}
