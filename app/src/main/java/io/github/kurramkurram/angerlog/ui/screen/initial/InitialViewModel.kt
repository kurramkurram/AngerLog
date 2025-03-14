package io.github.kurramkurram.angerlog.ui.screen.initial

import android.content.Context
import androidx.lifecycle.ViewModel
import io.github.kurramkurram.angerlog.data.repository.AgreementPolicyRepository

class InitialViewModel(private val agreementPolicyRepository: AgreementPolicyRepository) :
    ViewModel() {
    fun agree(context: Context) {
        agreementPolicyRepository.agree(context)
    }
}
