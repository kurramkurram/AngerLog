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
import androidx.compose.foundation.layout.imePadding
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
import androidx.compose.material.icons.outlined.Reviews
import androidx.compose.material.icons.sharp.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import io.github.kurramkurram.angerlog.ui.component.AngerLogModalBottomSheet
import io.github.kurramkurram.angerlog.ui.component.AngerLogOutlinedTextField
import io.github.kurramkurram.angerlog.ui.component.AngerLogVerticalDate
import io.github.kurramkurram.angerlog.ui.component.dialog.AngerLogBasicDialog
import io.github.kurramkurram.angerlog.ui.component.dialog.AngerLogDatePickerDialog
import io.github.kurramkurram.angerlog.ui.component.dialog.AngerLogTimePickerDialog
import io.github.kurramkurram.angerlog.ui.component.layout.AngerLogBackButtonLayout
import io.github.kurramkurram.angerlog.ui.screen.lookback.LookBackScreen
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
data class Register(val id: Long = 0, val date: Long = -1)

/**
 * 登録画面.
 *
 * @param modifier [Modifier]
 * @param register 登録データ.
 * @param onClickBack 戻る押下時の動作
 * @param onSaveClicked 保存する押下時の動作
 * @param viewModel 登録画面のViewModel
 */
@SuppressLint("NewApi")
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    register: Register,
    onClickBack: () -> Unit,
    onSaveClicked: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.initialize(register.id, register.date)
    }

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

/**
 * 登録画面のデータ取得後の画面.
 *
 * @param modifier [Modifier]
 * @param onClickBack 戻る押下時の動作
 * @param onSaveClicked 保存する押下時の動作
 * @param viewModel 登録画面のViewModel
 * @param state 登録画面の状態
 */
@Composable
fun RegisterScreenContent(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
    onSaveClicked: () -> Unit,
    viewModel: RegisterViewModel,
    state: RegisterUiState.Success,
) {
    BackHandler(enabled = true) {
        viewModel.onBackPressed()
    }

    LaunchedEffect(state) {
        if (state.goBack) {
            onClickBack()
        }
    }

    AngerLogBackButtonLayout(
        trailingText = stringResource((R.string.register_save)),
        onClickBack = { viewModel.onBackPressed() },
        onTrailingClick = {
            val success = viewModel.save()
            if (success) {
                onSaveClicked()
            }
        },
    ) {
        Column(
            modifier =
                Modifier
                    .verticalScroll(rememberScrollState())
                    .imePadding()
                    .padding(horizontal = 5.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            if (viewModel.showLookBackButton) {
                RegisterScreenLookBackButton(modifier = modifier, viewModel = viewModel)
            }

            val date = viewModel.date
            val time = viewModel.time

            AngerLogVerticalDate(
                date = date,
                time = time,
                onDateClick = { viewModel.showDatePicker() },
                onTimeClick = { viewModel.showTimePicker() },
            )

            if (state.showDatePicker) {
                RegisterScreenDatePickerDialog(modifier = modifier, viewModel = viewModel)
            }

            if (state.showTimePicker) {
                RegisterScreenTimePickerDialog(modifier = modifier, viewModel = viewModel)
            }

            if (state.showBackDialog) {
                RegisterScreenBackDialog(
                    modifier = modifier,
                    viewModel = viewModel
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
                RegisterScreenDeleteDialog(
                    modifier = modifier,
                    viewModel = viewModel
                ) { onClickBack() }
            }

            Spacer(Modifier.height(20.dp))

            if (state.showBadDateDialog) {
                RegisterScreenBadDateDialog(modifier = modifier, viewModel = viewModel)
            }

            if (state.showBottomSheet) {
                RegisterScreenLookBackBottomSheet(modifier = modifier, viewModel = viewModel)
            }
        }
    }
}

/**
 * 登録画面の項目
 *
 * @param modifier [Modifier]
 * @param title タイトル
 * @param onClickAssist アシストクリックしたときの動作
 * @param icon アイコン
 * @param content コンテンツ
 */
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

/**
 * 登録画面の怒りの強さ登録項目.
 *
 * @param modifier [Modifier]
 * @param selected 選択されている怒りの強さ
 * @param onSelected 怒りの強さ選択した時の動作
 */
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

/**
 * 振り返りボタン.
 *
 * @param modifier [Modifier]
 * @param viewModel 登録画面のViewModel
 */
@Composable
fun RegisterScreenLookBackButton(modifier: Modifier = Modifier, viewModel: RegisterViewModel) {
    Button(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
        onClick = { viewModel.showLookBackBottomSheet() },
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = modifier.padding(horizontal = 5.dp),
                imageVector = Icons.Outlined.Reviews,
                contentDescription = stringResource(R.string.register_look_back_button),
            )
            Text(stringResource(R.string.register_look_back_button))
        }
    }
}

/**
 * 日付選択ダイアログ.
 *
 * @param modifier [Modifier]
 * @param viewModel 登録画面のViewModel
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreenDatePickerDialog(modifier: Modifier = Modifier, viewModel: RegisterViewModel) {
    val datePickerState =
        rememberDatePickerState(initialSelectedDateMillis = viewModel.date.time)
    AngerLogDatePickerDialog(
        modifier = modifier,
        datePickerState = datePickerState,
        onConfirmRequest = { viewModel.updateDate(datePickerState) },
        onDismissRequest = { viewModel.closeDatePicker() },
    )
}

/**
 * 時間選択ダイアログ.
 *
 * @param modifier [Modifier]
 * @param viewModel 登録画面のViewModel
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreenTimePickerDialog(modifier: Modifier = Modifier, viewModel: RegisterViewModel) {
    val time = viewModel.time
    val timePickerState =
        rememberTimePickerState(
            initialHour = time.hour,
            initialMinute = time.minute,
            is24Hour = true,
        )
    AngerLogTimePickerDialog(
        modifier = modifier,
        timePickerState = timePickerState,
        onConfirmRequest = { viewModel.updateTime(it) },
        onDismissRequest = { viewModel.closeTimePicker() },
    )
}

/**
 * 戻るダイアログ.
 *
 * @param modifier [Modifier]
 * @param viewModel 登録画面のViewModel
 * @param onClickBack 戻る押下時の動作
 */
@Composable
fun RegisterScreenBackDialog(
    modifier: Modifier,
    viewModel: RegisterViewModel,
    onClickBack: () -> Unit,
) {
    AngerLogBasicDialog(
        modifier = modifier,
        title = stringResource((R.string.register)),
        descriptionContent = { Text(text = stringResource((R.string.register_back_dialog_description))) },
        confirmText = stringResource((R.string.register_back_dialog_positive_button)),
        dismissText = stringResource((R.string.register_back_dialog_negative_button)),
        onDismissRequest = { viewModel.closeBackDialog() },
        onDismissClick = { viewModel.closeBackDialog() },
    ) {
        onClickBack()
        viewModel.closeBackDialog()
    }
}

/**
 * 削除ダイアログ.
 *
 * @param modifier [Modifier]
 * @param viewModel 登録画面のViewModel
 * @param onClickBack 全画面に戻る動作
 */
@Composable
fun RegisterScreenDeleteDialog(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel,
    onClickBack: () -> Unit,
) {
    AngerLogBasicDialog(
        modifier = modifier,
        title = stringResource((R.string.register)),
        descriptionContent = { Text(text = stringResource((R.string.register_delete_dialog_description))) },
        confirmText = stringResource((R.string.register_delete_dialog_positive_button)),
        dismissText = stringResource((R.string.register_delete_dialog_negative_button)),
        onDismissRequest = { viewModel.closeDeleteDialog() },
        onDismissClick = { viewModel.closeDeleteDialog() },
    ) {
        viewModel.delete()
        onClickBack()
        viewModel.closeDeleteDialog()
    }
}

/**
 * 日付エラーダイアログ.
 *
 * @param modifier [Modifier]
 * @param viewModel 登録画面のViewModel
 */
@Composable
fun RegisterScreenBadDateDialog(modifier: Modifier = Modifier, viewModel: RegisterViewModel) {
    AngerLogBasicDialog(
        modifier = modifier,
        title = stringResource(R.string.register_bad_date_dialog_title),
        descriptionContent = { Text(text = stringResource(R.string.register_bad_date_dialog_description)) },
        confirmText = stringResource(R.string.register_bad_date_dialog_positive_button),
        onDismissRequest = { viewModel.closeBadDateDialog() },
        onDismissClick = {},
    ) { viewModel.closeBadDateDialog() }
}

/**
 * 振り返りボトムシート.
 *
 * @param modifier [Modifier]
 * @param viewModel 登録画面のViewModel
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreenLookBackBottomSheet(modifier: Modifier = Modifier, viewModel: RegisterViewModel) {
    val sheetState =
        rememberModalBottomSheetState(
            skipPartiallyExpanded = true,
            confirmValueChange = { newValue ->
                // Hidden状態への遷移を禁止
                newValue != SheetValue.Hidden
            },
        )
    val scope = rememberCoroutineScope()
    val close = {
        scope.launch {
            sheetState.hide()
        }.invokeOnCompletion { viewModel.closeLookBackBottomSheet() }
    }
    AngerLogModalBottomSheet(
        modifier = modifier.imePadding(),
        onDismissRequest = { viewModel.closeLookBackBottomSheet() },
        sheetState = sheetState,
    ) {
        LookBackScreen(
            modifier = modifier,
            onClickClose = { close() },
            selectedAngerLevel = viewModel.lookBackAngerLevel,
            onSelectedAngerLevel = { viewModel.updateLookBackAngerLevel(it) },
            whyAngerText = viewModel.lookBackWhyFeelAnger,
            onWhyAngerChanged = { viewModel.updateLookBackWhyFeelAnger(it) },
            adviceText = viewModel.lookBackAdvice,
            onAdviceChanged = { viewModel.updateLookBackAdvice(it) },
        ) {
            viewModel.saveLookBack()
            close()
        }
    }
}
