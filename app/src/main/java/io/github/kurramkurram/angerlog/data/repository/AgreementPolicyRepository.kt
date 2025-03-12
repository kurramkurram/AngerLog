package io.github.kurramkurram.angerlog.data.repository

import android.content.Context
import io.github.kurramkurram.angerlog.data.preference.KEY_POLICY_AGREEMENT
import io.github.kurramkurram.angerlog.data.preference.getFromPreference
import io.github.kurramkurram.angerlog.data.preference.saveToPreference

abstract class AgreementPolicyRepository {
    abstract fun agree(context: Context)

    abstract fun hasAgree(context: Context): Boolean
}

class AgreementPolicyRepositoryImpl : AgreementPolicyRepository() {

    override fun agree(context: Context) =
        saveToPreference(context, KEY_POLICY_AGREEMENT, 1)

    override fun hasAgree(context: Context): Boolean =
        getFromPreference(context, KEY_POLICY_AGREEMENT, 0) == 1
}