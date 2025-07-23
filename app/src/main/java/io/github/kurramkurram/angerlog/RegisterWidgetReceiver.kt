package io.github.kurramkurram.angerlog

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

/**
 * 怒り登録ウィジェットのレシーバー.
 */
class RegisterWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = AngerLogAppWidget()
}
