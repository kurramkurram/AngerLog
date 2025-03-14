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
import io.github.kurramkurram.angerlog.model.Time
import io.github.kurramkurram.angerlog.ui.AngerLevel
import io.github.kurramkurram.angerlog.ui.AngerLevelType
import io.github.kurramkurram.angerlog.util.DateConverter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

class RegisterViewModel(private val angerLogDataRepository: AngerLogDataRepository) : ViewModel() {
    private val _state = MutableStateFlow<RegisterUiState>(RegisterUiState.Success())
    val state = _state.asStateFlow()

    private var id: Long = 0

    private val currentTime = Calendar.getInstance()
    var date: Date by mutableStateOf(currentTime.time)
        private set

    var time by mutableStateOf(
        Time(
            currentTime.get(Calendar.HOUR_OF_DAY),
            currentTime.get(android.icu.util.Calendar.MINUTE),
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

    @OptIn(ExperimentalMaterial3Api::class)
    fun updateDate(input: DatePickerState) {
        date = input.selectedDateMillis?.let { Date(it) } ?: date
    }

    private fun updateDate(input: Date) {
        date = input
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun updateTime(input: TimePickerState) {
        updateTime(input.hour, input.minute)
    }

    private fun updateTime(
        hour: Int,
        minute: Int,
    ) {
        time = Time(hour, minute)
    }

    fun updateAngerLevel(input: AngerLevelType) {
        angerLevel = input
    }

    fun updateEvent(input: String) {
        event = input
    }

    fun updateDetail(input: String) {
        detail = input
    }

    fun updateThought(input: String) {
        thought = input
    }

    fun updatePlace(input: String) {
        place = input
    }

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

                updateDate(it.date)
                val calendar = DateConverter.dateToCalendar(it.date)
                updateTime(
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(android.icu.util.Calendar.MINUTE),
                )
                updateAngerLevel(AngerLevel().getAngerLevelType(it.level))
                updateEvent(it.event)
                updateDetail(it.detail)
                updateThought(it.thought)
                updatePlace(it.place)
            }
        }
    }

    /**
     * バック操作のダイアログを表示する.
     */
    fun showBackDialog() = _state.update { RegisterUiState.Success(showBackDialog = true) }

    /**
     * バック操作時のダイアログを閉じる.
     */
    fun closeBackDialog() = _state.update { RegisterUiState.Success(showBackDialog = false) }

    /**
     * 保存する、押下時の操作.
     * idが0の時には新規で作成
     * idが0出ない場合には更新
     */
    fun save() {
        viewModelScope.launch {
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
            if (angerLog.id == 0L) {
                angerLogDataRepository.save(angerLog)
            } else {
                angerLogDataRepository.update(angerLog)
            }
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
