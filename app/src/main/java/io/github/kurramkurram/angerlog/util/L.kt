package io.github.kurramkurram.angerlog.util

import android.util.Log
import io.github.kurramkurram.angerlog.BuildConfig

/**
 * ログクラス.
 */
class L {
    companion object {
        private const val TAG = "AngerLog"

        /**
         * デバッグログ
         *
         * @param message ログメッセージ
         */
        @JvmStatic
        fun d(message: String) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, message)
            }
        }

        /**
         * エラーログ
         *
         * @param message エラーメッセージ
         */
        @JvmStatic
        fun e(message: String) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, message)
            }
        }
    }
}
