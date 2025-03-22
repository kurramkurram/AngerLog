package io.github.kurramkurram.angerlog.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * リクエストする権限.
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
val requestPermission = Manifest.permission.POST_NOTIFICATIONS

/**
 * 権限が許可されているか判定する.
 *
 * @param context [Context]
 * @param permission 判定を行う権限.
 * @return true: 許可されている
 */
fun isPermissionGranted(
    context: Context,
    permission: String,
): Boolean {
    return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
}
