package io.github.kurramkurram.angerlog.data.repository

import android.content.Context
import io.github.kurramkurram.angerlog.data.preference.KEY_POLICY_AGREEMENT
import io.github.kurramkurram.angerlog.data.preference.getFromPreference
import io.github.kurramkurram.angerlog.data.preference.saveToPreference

/**
 * 同意していない.
 */
private const val NOT_AGREED = 0

/**
 * 同意済み.
 */
private const val AGREED = 1

/**
 * 利用規約の同意に関するRepository.
 */
abstract class AgreementPolicyRepository {
    /**
     * 同意したことを保存する.
     *
     * @param context [Context]
     */
    abstract fun agree(context: Context)

    /**
     * 同意済みかどうかを判定する.
     *
     * @param context [Context]
     * @return true: 同意済み
     */
    abstract fun hasAgree(context: Context): Boolean
}

class AgreementPolicyRepositoryImpl : AgreementPolicyRepository() {
    /**
     * 同意したことを保存する.
     *
     * @param context [Context]
     */
    override fun agree(context: Context) = saveToPreference(context, KEY_POLICY_AGREEMENT, AGREED)

    /**
     * 同意済みかどうかを判定する.
     *
     * @param context [Context]
     * @return true: 同意済み
     */
    override fun hasAgree(context: Context): Boolean =
        getFromPreference(context, KEY_POLICY_AGREEMENT, NOT_AGREED) == AGREED
}
