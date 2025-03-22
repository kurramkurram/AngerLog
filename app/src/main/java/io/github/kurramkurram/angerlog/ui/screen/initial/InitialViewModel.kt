package io.github.kurramkurram.angerlog.ui.screen.initial

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Reviews
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import io.github.kurramkurram.angerlog.R
import io.github.kurramkurram.angerlog.data.repository.AgreementPolicyRepository

/**
 * 初期画面のViewModel.
 *
 * @param agreementPolicyRepository 利用規約への同意状態を判定するRepository
 */
class InitialViewModel(private val agreementPolicyRepository: AgreementPolicyRepository) :
    ViewModel() {
    /**
     * 利用規約に同意した.
     *
     * @param context [Context]
     */
    fun agree(context: Context) {
        agreementPolicyRepository.agree(context)
    }

    /**
     * アプリ説明のコンテンツを返す.
     *
     * @return アプリ説明のコンテンツ
     */
    @Composable
    fun getDetails(): List<InitialDetailsDto> =
        listOf(
            InitialDetailsDto(
                imageVector = Icons.Filled.Whatshot,
                description = stringResource(R.string.initial_details_record_anger),
            ),
            InitialDetailsDto(
                imageVector = Icons.Outlined.Reviews,
                description = stringResource(R.string.initial_details_record_look_back),
            ),
            InitialDetailsDto(
                imageVector = Icons.Outlined.Info,
                description = stringResource(R.string.initial_details_tips),
            ),
        )
}
