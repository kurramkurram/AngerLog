package io.github.kurramkurram.angerlog.ui.screen.reminder

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.kurramkurram.angerlog.BuildConfig
import io.github.kurramkurram.angerlog.ui.component.layout.AngerLogBackButtonLayout
import kotlinx.serialization.Serializable

@Serializable
object Reminder

@Composable
fun ReminderScreen(modifier: Modifier = Modifier, onClickBack: () -> Unit) {
    AngerLogBackButtonLayout(modifier = modifier, onClickBack = onClickBack) {
        // 説明
        // リマインダーを登録すると登録した時間にアンガーログアプリから通知されます。
        // その日感じた怒りを登録しわすれないようにリマインダーをONにしましょう。

        // リマインダー
        // ON <-> OFFの切り替え
        // ONにしたら時間の設定ができるダイアログが出る
        // 時間を設定したら、時間を表示
        // 時間を変更する場合には、時間をタップでダイアログが出る
        // OFFにしたら時間は出ない。
        // ONにするときに通知の権限がOFFの場合には、設定に飛ばす。
        // 戻ってきてONになっていたらダイアログを出す？

    }
}

/**
 * 設定アプリの本アプリ通知設定画面を起動する.
 *
 * @param context [Context]
 */
private fun startNotificationSetting(context: Context) {
    Intent().apply {
        action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
        putExtra(Settings.EXTRA_APP_PACKAGE, BuildConfig.APPLICATION_ID)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(this)
    }
}