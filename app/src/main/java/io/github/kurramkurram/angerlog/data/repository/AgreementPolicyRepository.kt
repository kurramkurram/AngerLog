package io.github.kurramkurram.angerlog.data.repository

import android.content.Context
import io.github.kurramkurram.angerlog.data.preference.KEY_POLICY_AGREEMENT
import io.github.kurramkurram.angerlog.data.preference.KEY_POLICY_AGREEMENT_VERSION
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
 * 利用規約のバージョン
 * - 1: 初版
 * - 2: Data Retention Policyを更新版
 */
private const val POLICY_VERSION = 2

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
     * 過去に一度でも同意済みかどうかを判定する.
     *
     * @param context [Context]
     * @return true: 同意済み
     */
    abstract fun hasAgree(context: Context): Boolean

    /**
     * 最新の利用規約に同意したことを保存する.
     *
     * @param context [Context]
     */
    abstract fun agreeLatest(context: Context)

    /**
     * 最新の規約に同意しているかどうかを判定する.
     *
     * @param context [Context]
     * @return 同意済み
     */
    abstract fun hasAgreeLatest(context: Context): Boolean
}

class AgreementPolicyRepositoryImpl : AgreementPolicyRepository() {
    /**
     * 同意したことを保存する.
     *
     * @param context [Context]
     */
    override fun agree(context: Context) = saveToPreference(context, KEY_POLICY_AGREEMENT, AGREED)

    /**
     * 過去に一度でも同意済みかどうかを判定する.
     *
     * @param context [Context]
     * @return true: 同意済み
     */
    override fun hasAgree(context: Context): Boolean =
        getFromPreference(context, KEY_POLICY_AGREEMENT, NOT_AGREED) == AGREED

    /**
     * 最新の利用規約に同意したことを保存する.
     *
     * @param context [Context]
     */
    override fun agreeLatest(context: Context) =
        saveToPreference(context, KEY_POLICY_AGREEMENT_VERSION, POLICY_VERSION)

    /**
     * 最新の規約に同意しているかどうかを判定する.
     *
     * @param context [Context]
     * @return 同意済み
     */
    override fun hasAgreeLatest(context: Context): Boolean =
        getFromPreference(context, KEY_POLICY_AGREEMENT_VERSION, 0) == POLICY_VERSION
}
