package io.github.kurramkurram.angerlog.ui.screen.register

sealed class RegisterUiState {
    data class Success(
        val showDatePicker: Boolean = false,
        val showTimePicker: Boolean = false,
        val showBackDialog: Boolean = false,
        val showDeleteDialog: Boolean = false,
        val showBottomSheet: Boolean = false,
        val goBack: Boolean = false,
    ) : RegisterUiState()
}
