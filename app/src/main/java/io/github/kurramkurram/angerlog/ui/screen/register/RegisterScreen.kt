package io.github.kurramkurram.angerlog.ui.screen.register

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.sharp.CheckCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.kurramkurram.angerlog.R
import io.github.kurramkurram.angerlog.ui.AngerLevel
import io.github.kurramkurram.angerlog.ui.AngerLevelType
import io.github.kurramkurram.angerlog.ui.component.AngerLogOutlinedTextField
import io.github.kurramkurram.angerlog.ui.component.AngerLogVerticalDate
import io.github.kurramkurram.angerlog.ui.component.dialog.AngerLogBasicDialog
import io.github.kurramkurram.angerlog.ui.component.dialog.AngerLogDatePickerDialog
import io.github.kurramkurram.angerlog.ui.component.dialog.AngerLogTimePickerDialog
import io.github.kurramkurram.angerlog.ui.component.layout.AngerLogBackButtonLayout
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
data class Register(val id: Long = 0, val date: Long = -1)

/**
 * 登録画面.
 *
 * ViewModelにTextFieldの状態を持たせるベストプラクティス
 * https://developer.android.com/develop/ui/compose/text/user-input?hl=ja#state-practices
 */
@SuppressLint("NewApi")
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    id: Long = 0,
    inputDate: Long = -1,
    onSaveClicked: () -> Unit,
    onClickBack: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    viewModel.initialize(id, inputDate)

    when (state) {
        is RegisterUiState.Success -> {
            RegisterScreenContent(
                modifier = modifier,
                onClickBack = onClickBack,
                onSaveClicked = onSaveClicked,
                viewModel = viewModel,
                state = state as RegisterUiState.Success,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreenContent(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
    onSaveClicked: () -> Unit,
    viewModel: RegisterViewModel,
    state: RegisterUiState.Success,
) {
    BackHandler(enabled = true) {
        viewModel.showBackDialog()
    }

    AngerLogBackButtonLayout(
        leadingText = stringResource((R.string.register_save)),
        onClickBack = { viewModel.showBackDialog() },
        onClickLeading = {
            onSaveClicked()
            viewModel.save()
        },
    ) {
        Column(
            modifier =
                Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 5.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            val date = viewModel.date
            val time = viewModel.time

            Spacer(Modifier.height(10.dp))

            AngerLogVerticalDate(
                date = date,
                time = time,
                onDateClick = { viewModel.showDatePicker() },
                onTimeClick = { viewModel.showTimePicker() },
            )

            if (state.showDatePicker) {
                val datePickerState =
                    rememberDatePickerState(initialSelectedDateMillis = date.time)
                AngerLogDatePickerDialog(
                    datePickerState = datePickerState,
                    onConfirmRequest = { viewModel.updateDate(datePickerState) },
                    onDismissRequest = { viewModel.closeDatePicker() },
                )
            }

            if (state.showTimePicker) {
                val timePickerState =
                    rememberTimePickerState(
                        initialHour = time.hour,
                        initialMinute = time.minute,
                        is24Hour = true,
                    )
                AngerLogTimePickerDialog(
                    timePickerState = timePickerState,
                    onConfirmRequest = { viewModel.updateTime(it) },
                    onDismissRequest = { viewModel.closeTimePicker() },
                )
            }

            if (state.showBackDialog) {
                AngerLogBasicDialog(
                    modifier = modifier,
                    title = stringResource((R.string.register)),
                    description = stringResource((R.string.register_back_dialog_description)),
                    confirmText = stringResource((R.string.register_back_dialog_positive_button)),
                    dismissText = stringResource((R.string.register_back_dialog_negative_button)),
                    onDismissRequest = { viewModel.closeBackDialog() },
                ) { onClickBack() }
            }

            RegisterScreenItem(
                title = stringResource(R.string.register_anger_level_title),
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Whatshot,
                        contentDescription = stringResource(R.string.register_anger_level_title),
                    )
                },
            ) {
                RegisterScreenAngerLevel(selected = viewModel.angerLevel) {
                    viewModel.updateAngerLevel(
                        it,
                    )
                }
            }

            RegisterScreenItem(
                title = stringResource(R.string.register_anger_event_title),
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Event,
                        contentDescription = stringResource(R.string.register_anger_event_title),
                    )
                },
            ) {
                AngerLogOutlinedTextField(
                    value = viewModel.event,
                    onValueChange = { viewModel.updateEvent(it) },
                    hint = stringResource((R.string.register_anger_event_hint)),
                    singleLine = true,
                )
            }

            RegisterScreenItem(
                title = stringResource(R.string.register_anger_event_detail_title),
                icon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.List,
                        contentDescription = stringResource(R.string.register_anger_event_detail_title),
                    )
                },
            ) {
                AngerLogOutlinedTextField(
                    value = viewModel.detail,
                    onValueChange = { viewModel.updateDetail(it) },
                    hint = stringResource((R.string.register_anger_event_detail_hint)),
                    height = 100.dp,
                )
            }

            RegisterScreenItem(
                title = stringResource(R.string.register_anger_event_thought_title),
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Psychology,
                        contentDescription = stringResource(R.string.register_anger_event_thought_title),
                    )
                },
            ) {
                AngerLogOutlinedTextField(
                    value = viewModel.thought,
                    onValueChange = { viewModel.updateThought(it) },
                    hint = stringResource(R.string.register_anger_event_thought_hint),
                    height = 100.dp,
                )
            }

            RegisterScreenItem(
                title = stringResource(R.string.register_anger_event_place_title),
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Place,
                        contentDescription = stringResource(R.string.register_anger_event_place_title),
                    )
                },
            ) {
                AngerLogOutlinedTextField(
                    value = viewModel.place,
                    onValueChange = { viewModel.updatePlace(it) },
                    hint = stringResource(R.string.register_anger_event_place_hint),
                    singleLine = true,
                )
            }

            Spacer(Modifier.height(10.dp))

            Text(
                modifier =
                    modifier
                        .fillMaxWidth()
                        .clickable { viewModel.showDeleteDialog() }
                        .padding(vertical = 10.dp),
                text = stringResource(R.string.register_delete),
                color = Color.Red,
                textAlign = TextAlign.Center,
            )

            if (state.showDeleteDialog) {
                AngerLogBasicDialog(
                    modifier = modifier,
                    title = stringResource((R.string.register)),
                    description = stringResource((R.string.register_delete_dialog_description)),
                    confirmText = stringResource((R.string.register_delete_dialog_positive_button)),
                    dismissText = stringResource((R.string.register_delete_dialog_negative_button)),
                    onDismissRequest = { viewModel.closeDeleteDialog() },
                ) {
                    viewModel.delete()
                    onClickBack()
                }
            }

            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
fun RegisterScreenItem(
    modifier: Modifier = Modifier,
    title: String = "",
    onClickAssist: (() -> Unit)? = null,
    icon: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    Column(modifier = modifier.padding(vertical = 4.dp)) {
        Row(
            modifier = modifier.padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            icon()

            Text(modifier = modifier.padding(horizontal = 10.dp), text = title)

            onClickAssist?.let {
                Image(
                    imageVector = Icons.Sharp.CheckCircle,
                    modifier = modifier.clickable { onClickAssist() },
                    contentDescription = "",
                )
            }
        }
        content()
    }
}

@Composable
fun RegisterScreenAngerLevel(
    modifier: Modifier = Modifier,
    selected: AngerLevelType?,
    onSelected: (AngerLevelType) -> Unit,
) {
    Row(
        modifier
            .fillMaxWidth()
            .border(
                border = BorderStroke(1.dp, Color.DarkGray),
                shape = MaterialTheme.shapes.small,
            )
            .background(
                color = MaterialTheme.colorScheme.onPrimary,
                shape = MaterialTheme.shapes.small,
            )
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        for ((index, level) in AngerLevelType.entries.withIndex()) {
            Box(modifier = modifier.clickable { onSelected(level) }) {
                Text(
                    modifier =
                        modifier
                            .clip(CircleShape)
                            .border(
                                border =
                                    BorderStroke(
                                        width = 2.dp,
                                        color =
                                            if (selected == level) {
                                                MaterialTheme.colorScheme.primaryContainer
                                            } else {
                                                Color.Transparent
                                            },
                                    ),
                                shape = CircleShape,
                            )
                            .background(color = AngerLevel().select(level).getColor())
                            .padding(horizontal = 20.dp, vertical = 5.dp),
                    text = "${index + 1}",
                )
            }
        }
    }
}
