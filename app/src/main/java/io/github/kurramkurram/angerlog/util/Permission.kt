package io.github.kurramkurram.angerlog.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
val requestPermission = Manifest.permission.POST_NOTIFICATIONS

fun isPermissionGranted(
    context: Context,
    permission: String,
): Boolean {
    return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
}
