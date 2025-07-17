package io.github.kurramkurram.angerlog.ui.screen.register

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.kurramkurram.angerlog.data.repository.AngerLogDataRepository
import io.github.kurramkurram.angerlog.model.AngerLog
import io.github.kurramkurram.angerlog.model.ShowLookBack
import io.github.kurramkurram.angerlog.model.Time
import io.github.kurramkurram.angerlog.ui.AngerLevel
import io.github.kurramkurram.angerlog.ui.AngerLevelType
import io.github.kurramkurram.angerlog.util.DateConverter
import io.github.kurramkurram.angerlog.util.L
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

/**
 * 登録画面のViewModel.
 *
 * @param angerLogDataRepository 怒りの記録のRepository
 * @param calendar [Calendar]
 * @param now 現在時刻
 */
class RegisterViewModel(
    private val angerLogDataRepository: AngerLogDataRepository,
    calendar: Calendar = Calendar.getInstance(),
    private val now: Long = calendar.timeInMillis,
) : ViewModel() {
    private val _state = MutableStateFlow<RegisterUiState>(RegisterUiState.Success())
    val state = _state.asStateFlow()

    private var id: Long = 0
    private var initail = AngerLog(date = calendar.time)

    var showLookBackButton: Boolean = false
        private set

    var date: Date by mutableStateOf(calendar.time)
        private set

    var time by mutableStateOf(
        Time(
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(android.icu.util.Calendar.MINUTE),
        ),
    )
        private set

    var angerLevel by mutableStateOf(AngerLevelType.LEVEL_1)
        private set

    var event by mutableStateOf("")
        private set

    var detail by mutableStateOf("")
        private set

    var thought by mutableStateOf("")
        private set

    var place by mutableStateOf("")
        private set

    var lookBackAngerLevel by mutableStateOf(angerLevel)
        private set

    var lookBackWhyFeelAnger by mutableStateOf("")
        private set

    var lookBackAdvice by mutableStateOf("")
        private set

    /**
     * 日付を更新.
     *
     * @param input 更新する日付
     */
    @OptIn(ExperimentalMaterial3Api::class)
    fun updateDate(input: DatePickerState) {
        date = input.selectedDateMillis?.let { Date(it) } ?: date
    }

    /**
     * 日付を更新.
     *
     * @param input 更新する日付
     */
    private fun updateDate(input: Date) {
        date = input
    }

    /**
     * 時間を更新
     *
     * @param input 更新する時間
     */
    @OptIn(ExperimentalMaterial3Api::class)
    fun updateTime(input: TimePickerState) {
        updateTime(input.hour, input.minute)
    }

    /**
     * 時間を更新
     *
     * @param hour 更新する時
     * @param minute 更新する分
     */
    private fun updateTime(
        hour: Int,
        minute: Int,
    ) {
        time = Time(hour, minute)
    }

    /**
     * 怒りの強さを更新.
     *
     * @param input 更新する怒りの強さ
     */
    fun updateAngerLevel(input: AngerLevelType) {
        angerLevel = input
    }

    /**
     * できごとを更新.
     *
     * @param input 更新するできごと
     */
    fun updateEvent(input: String) {
        event = input
    }

    /**
     * できごと詳細を更新.
     *
     * @param input 更新するできごと詳細
     */
    fun updateDetail(input: String) {
        detail = input
    }

    /**
     * 思ったことを更新.
     *
     * @param input 更新する思ったこと
     */
    fun updateThought(input: String) {
        thought = input
    }

    /**
     * 場所を更新.
     *
     * @param input 更新する場所
     */
    fun updatePlace(input: String) {
        place = input
    }

    /**
     * 振り返りの怒りの強さを更新.
     *
     * @param input 更新する振り返りの怒りの強さ
     */
    fun updateLookBackAngerLevel(input: AngerLevelType) {
        lookBackAngerLevel = input
    }

    /**
     * 振り返りのなぜ怒りを感じたかを更新.
     *
     * @param input 更新する振り返りのなぜ怒りを感じたか
     */
    fun updateLookBackWhyFeelAnger(input: String) {
        lookBackWhyFeelAnger = input
    }

    /**
     * 振り返りのアドバイスを更新.
     *
     * @param input 更新する振り返りのアドバイス
     */
    fun updateLookBackAdvice(input: String) {
        lookBackAdvice = input
    }

    /**
     * 初期の登録画面の情報を取得する.
     *
     * @param id データベースの一意のid
     * @param inputDate 日付
     */
    fun initialize(
        id: Long,
        inputDate: Long,
    ) {
        this.id = id

        if (inputDate != -1L) {
            updateDate(Date(inputDate))
        }
        if (id <= 0L) return
        viewModelScope.launch {
            angerLogDataRepository.getById(id).collect {
                if (it == null) return@collect

                initail = it

                updateDate(it.date)
                val c = DateConverter.dateToCalendar(it.date)
                updateTime(
                    c.get(Calendar.HOUR_OF_DAY),
                    c.get(android.icu.util.Calendar.MINUTE),
                )
                val angerLevel = AngerLevel()
                updateAngerLevel(angerLevel.getAngerLevelType(it.level))
                updateEvent(it.event)
                updateDetail(it.detail)
                updateThought(it.thought)
                updatePlace(it.place)
                updateLookBackAngerLevel(angerLevel.getAngerLevelType(it.lookBackLevel))
                if (lookBackWhyFeelAnger.isEmpty()) {
                    updateLookBackWhyFeelAnger(it.lookBackWhyAnger)
                }
                if (lookBackAdvice.isEmpty()) {
                    updateLookBackAdvice(it.lookBackAdvice)
                }

                showLookBackButton =
                    ShowLookBack(
                        now = now,
                        logDate = c.timeInMillis,
                    ).showLookBack
            }
        }
    }

    /**
     * バック操作.
     *
     * 内容に変更があればダイアログ表示状態にする.
     * 内容に変更がなければ、前の画面に戻る.
     */
    fun onBackPressed() {
        val anger = AngerLevel()
        val angerLog =
            AngerLog(
                id = id,
                date = DateConverter.dateTimeToDate(date, time),
                level = anger.getLevel(angerLevel),
                event = event,
                detail = detail,
                thought = thought,
                place = place,
                lookBackLevel = anger.getLevel(lookBackAngerLevel),
                lookBackWhyAnger = lookBackWhyFeelAnger,
                lookBackAdvice = lookBackAdvice,
            )
        if (initail != angerLog) {
            _state.update { RegisterUiState.Success(showBackDialog = true) }
        } else {
            _state.update { RegisterUiState.Success(showBackDialog = false, goBack = true) }
        }
    }

    /**
     * バック操作時のダイアログを閉じる.
     */
    fun closeBackDialog() = _state.update { RegisterUiState.Success(showBackDialog = false) }

    /**
     * 保存する、押下時の操作.
     * idが0の時には新規で作成
     * idが0出ない場合には更新
     *
     * @return 保存の成否
     */
    fun save(): Boolean {
        val angerDate = DateConverter.dateTimeToDate(date, time)
        val now = Date()
        if (angerDate > now) {
            showBadDateDialog()
            return false
        }

        val anger = AngerLevel()
        val angerLog =
            AngerLog(
                id = id,
                date = angerDate,
                level = anger.getLevel(angerLevel),
                event = event,
                detail = detail,
                thought = thought,
                place = place,
                lookBackLevel = anger.getLevel(lookBackAngerLevel),
                lookBackWhyAnger = lookBackWhyFeelAnger,
                lookBackAdvice = lookBackAdvice,
            )
        viewModelScope.launch {
            if (angerLog.id == 0L) {
                angerLogDataRepository.save(angerLog)
            } else {
                angerLogDataRepository.update(angerLog)
            }
        }
        return true
    }

    /**
     * 振り返りの保存する押下時の動作.
     */
    fun saveLookBack() {
        val anger = AngerLevel()
        val angerLog =
            AngerLog(
                id = id,
                date = DateConverter.dateTimeToDate(date, time),
                level = anger.getLevel(angerLevel),
                event = event,
                detail = detail,
                thought = thought,
                place = place,
                lookBackLevel = anger.getLevel(lookBackAngerLevel),
                lookBackWhyAnger = lookBackWhyFeelAnger,
                lookBackAdvice = lookBackAdvice,
            )
        viewModelScope.launch {
            angerLogDataRepository.update(angerLog)
        }
    }

    /**
     * DatePickerを表示する.
     * TimePickerが表示中の場合には非表示にする
     */
    fun showDatePicker() =
        _state.update {
            RegisterUiState.Success(showDatePicker = true, showTimePicker = false)
        }

    /**
     * DatePickerを閉じる.
     */
    fun closeDatePicker() = _state.update { RegisterUiState.Success(showDatePicker = false) }

    /**
     * TimePickerを表意zする.
     * DatePickerを表示中の場合には非表示にする.
     */
    fun showTimePicker() =
        _state.update {
            RegisterUiState.Success(showDatePicker = false, showTimePicker = true)
        }

    /**
     * TimePickerを閉じる.
     */
    fun closeTimePicker() = _state.update { RegisterUiState.Success(showTimePicker = false) }

    /**
     * 削除ダイアログを表示する.
     */
    fun showDeleteDialog() = _state.update { RegisterUiState.Success(showDeleteDialog = true) }

    /**
     * 削除ダイアログを閉じる.
     */
    fun closeDeleteDialog() = _state.update { RegisterUiState.Success(showDeleteDialog = false) }

    /**
     *
     */
    private fun showBadDateDialog() =
        _state.update { RegisterUiState.Success(showBadDateDialog = true) }

    /**
     *
     */
    fun closeBadDateDialog() = _state.update { RegisterUiState.Success(showBadDateDialog = false) }

    /**
     * 振り返りのボトムシートを表示する.
     */
    fun showLookBackBottomSheet() =
        _state.update { RegisterUiState.Success(showBottomSheet = true) }

    /**
     * 振り返りのボトムシートを閉じる.
     */
    fun closeLookBackBottomSheet() =
        _state.update { RegisterUiState.Success(showBottomSheet = false) }

    /**
     * 削除する.
     */
    fun delete() {
        val angerLog =
            AngerLog(
                id = id,
                date = DateConverter.dateTimeToDate(date, time),
                level = AngerLevel().getLevel(angerLevel),
                event = event,
                detail = detail,
                thought = thought,
                place = place,
            )
        viewModelScope.launch {
            angerLogDataRepository.delete(angerLog = angerLog)
        }
    }
}
