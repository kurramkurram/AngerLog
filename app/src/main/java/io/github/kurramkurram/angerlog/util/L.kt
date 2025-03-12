package io.github.kurramkurram.angerlog.util

import android.util.Log
import io.github.kurramkurram.angerlog.BuildConfig

class L {

    companion object {
        private const val TAG = "AngerLog"

        @JvmStatic
        fun d(message: String) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, message)
            }
        }

        @JvmStatic
        fun e(message: String) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, message)
            }
        }
    }
}