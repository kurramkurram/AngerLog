package io.github.kurramkurram.angerlog

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

/**
 * ウィジェットの追加のアクション.
 */
const val ACTION_PIN_WIDGET = "io.github.kurramkurram.angerlog.ACTION_PIN_WIDGET"

/**
 * ウィジェット追加を検知するBroadcastReceiver.
 */
class PinWidgetBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context?,
        intent: Intent?,
    ) {
        if (intent?.action == ACTION_PIN_WIDGET) {
            Toast.makeText(
                context,
                context?.resources?.getString(R.string.setting_widget_pin_message),
                Toast.LENGTH_SHORT,
            ).show()
        }
    }
}
