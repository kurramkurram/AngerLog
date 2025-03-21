package io.github.kurramkurram.angerlog.ui.screen.initial

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Reviews
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import io.github.kurramkurram.angerlog.R
import io.github.kurramkurram.angerlog.data.repository.AgreementPolicyRepository

class InitialViewModel(private val agreementPolicyRepository: AgreementPolicyRepository) :
    ViewModel() {
    fun agree(context: Context) {
        agreementPolicyRepository.agree(context)
    }

    @Composable
    fun getDetails(): List<DetailsDto> =
        listOf(
            DetailsDto(
                imageVector = Icons.Filled.Whatshot,
                description = stringResource(R.string.initial_details_record_anger),
            ),
            DetailsDto(
                imageVector = Icons.Outlined.Reviews,
                description = stringResource(R.string.initial_details_record_look_back),
            ),
            DetailsDto(
                imageVector = Icons.Outlined.Info,
                description = stringResource(R.string.initial_details_tips),
            ),
        )
}

data class DetailsDto(
    val imageVector: ImageVector,
    val description: String,
)
