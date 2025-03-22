package io.github.kurramkurram.angerlog.ui.screen.setting

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.play.core.review.ReviewManagerFactory
import io.github.kurramkurram.angerlog.BuildConfig
import io.github.kurramkurram.angerlog.R
import io.github.kurramkurram.angerlog.util.L
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Date

private const val TAG = "SettingScreen"

@Serializable
object Setting

/**
 * 設定画面.
 *
 * @param modifier [Modifier]
 * @param onItemTipsClick お役立ちTips押下時の動作
 * @param onPolicyClick 利用規約押下時の動作
 * @param onLicenseClick ライセンス押下時の動作
 */
@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    onItemTipsClick: () -> Unit,
    onPolicyClick: () -> Unit,
    onLicenseClick: () -> Unit,
) {
    val context = LocalContext.current

    Column(
        modifier
            .padding(top = 20.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Text(
            modifier =
            modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            text = stringResource(R.string.setting),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
        )

        SettingScreenSectionItem {
            // お役立ち
            SettingScreenItem(leading = stringResource(R.string.setting_tips)) { onItemTipsClick() }
        }

        SettingScreenSectionItem {
            // 通知設定
            SettingScreenItem(
                leading = stringResource(R.string.setting_notification),
                iconType = SettingLeadingIconType.OpenInNew,
            ) {
                startNotificationSetting(
                    context,
                )
            }
        }

        SettingScreenSectionItem {
            // アプリバージョン
            SettingScreenItem(
                leading = stringResource(R.string.setting_application_version),
                trailing = BuildConfig.VERSION_NAME,
            ) { }
            // アプリケーションプライバシーポリシー
            SettingScreenItem(leading = stringResource(R.string.setting_policy)) { onPolicyClick() }
            // OSS
            SettingScreenItem(leading = stringResource(R.string.setting_License)) { onLicenseClick() }
        }

        SettingScreenSectionItem {
            // アプリshare
            SettingScreenItem(leading = stringResource(R.string.setting_share)) { startShare(context) }
            // ほかのアプリ
            SettingScreenItem(
                leading = stringResource(R.string.setting_other_app),
                iconType = SettingLeadingIconType.OpenInNew,
            ) {
                startOtherApp(
                    context,
                )
            }
            // アプリを評価する
            SettingScreenItem(leading = stringResource(R.string.setting_evaluate)) {
                startReview(
                    context,
                )
            }
        }

        // 問い合わせ
        SettingScreenSectionItem {
            SettingScreenItem(
                leading = stringResource(R.string.setting_question),
                iconType = SettingLeadingIconType.OpenInNew,
            ) {
                startActivityQuestion(
                    context,
                )
            }
        }
    }
}

/**
 * 問い合わせはこちら押下時の動作.
 * メールアプリを起動する.
 *
 * @param context [Context]
 */
@SuppressLint("SimpleDateFormat")
private fun startActivityQuestion(context: Context) {
    Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, arrayOf("kurram.dev@gmail.com"))
        val subject = context.resources.getString(R.string.setting_question_mail_subject)
        putExtra(Intent.EXTRA_SUBJECT, subject)

        val text =
            context.resources.getString(
                R.string.setting_question_text,
                BuildConfig.VERSION_NAME,
                Build.VERSION.SDK_INT,
                Build.MODEL,
                SimpleDateFormat("yyyy/MM/dd").format(Date()),
            )
        putExtra(Intent.EXTRA_TEXT, text)

        try {
            context.startActivity(this)
        } catch (e: ActivityNotFoundException) {
            L.e("$TAG#onClick $e")
        }
    }
}

/**
 * このアプリをシェアする押下時の動作.
 * 共有のボトムシートを表示する.
 *
 * @param context
 */
private fun startShare(context: Context) {
    Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, context.resources.getString(R.string.setting_share_text))
        try {
            context.startActivity(Intent.createChooser(this, null))
        } catch (e: Exception) {
            L.e("$TAG#onClick $e")
        }
    }
}

/**
 * お知らせの設定押下時の動作.
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

/**
 * ほかのアプリ押下時の動作.
 * Google Playのデベロッパー検索画面を起動する.
 *
 * @param context [Context]
 */
private fun startOtherApp(context: Context) {
    Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse("https://play.google.com/store/apps/developer?id=Kurram")
        try {
            context.startActivity(this)
        } catch (e: ActivityNotFoundException) {
            L.e("$TAG#onClick $e")
        }
    }
}

/**
 * このアプリを評価する押下時の動作.
 *
 * @param context [Context]
 */
private fun startReview(context: Context) {
    val manager = ReviewManagerFactory.create(context as Activity)
    val request = manager.requestReviewFlow()
    request.addOnCompleteListener {
        if (it.isSuccessful) {
            val info = it.result
            manager.launchReviewFlow(context, info).apply {
                addOnCompleteListener { Log.d(TAG, "#onClick onComplete") }
                addOnSuccessListener { Log.d(TAG, "#onClick onSuccess") }
                addOnCanceledListener { Log.d(TAG, "#onClick onCancel") }
                addOnFailureListener { Log.d(TAG, "#onClick onFailure") }
            }
        } else {
            Log.d(TAG, "#onClick app_assessment task = $it")
        }
    }
}

/**
 * 設定画面のセクション項目.
 *
 * @param modifier [Modifier]
 * @param content コンテンツ
 */
@Composable
fun SettingScreenSectionItem(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier =
        modifier
            .padding(horizontal = 8.dp, vertical = 10.dp)
            .background(
                color = MaterialTheme.colorScheme.onPrimary,
                shape = MaterialTheme.shapes.medium,
            ),
    ) { content() }
}

/**
 * 設定画面の各項目.
 *
 * @param modifier [Modifier]
 * @param leading 説明
 * @param trailing 項目に表示する文字列
 * @param iconType アイコンのタイプ（[trailing]が未指定の場合に表示）
 * @param onItemClick 項目押下時の動作
 */
@Composable
fun SettingScreenItem(
    modifier: Modifier = Modifier,
    leading: String,
    trailing: String = "",
    iconType: SettingLeadingIconType = SettingLeadingIconType.NextScreen,
    onItemClick: () -> Unit,
) {
    Row(
        modifier =
        modifier
            .background(
                color = Color.Transparent,
                shape = MaterialTheme.shapes.medium,
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                onItemClick()
            }
            .padding(horizontal = 10.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier =
            modifier
                .weight(1f)
                .padding(horizontal = 10.dp),
            text = leading,
        )

        Icon(
            modifier =
            modifier.graphicsLayer {
                alpha = if (trailing.isEmpty()) 1f else 0f
            },
            imageVector = iconType.imageVector,
            contentDescription = stringResource(R.string.setting_next_cd),
        )

        Text(
            modifier = modifier.graphicsLayer { alpha = if (trailing.isNotEmpty()) 1f else 0f },
            text = trailing,
        )
    }
}
