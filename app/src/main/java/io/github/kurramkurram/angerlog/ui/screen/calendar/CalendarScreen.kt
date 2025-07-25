package io.github.kurramkurram.angerlog.ui.screen.calendar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.kurramkurram.angerlog.R
import io.github.kurramkurram.angerlog.ui.AngerLevel
import io.github.kurramkurram.angerlog.ui.CustomDayOfWeek
import io.github.kurramkurram.angerlog.ui.component.AngerLogHorizontalDivider
import io.github.kurramkurram.angerlog.ui.component.calendarpicker.AngerLogCalendarPicker
import io.github.kurramkurram.angerlog.ui.component.calendarpicker.CalendarPickerUiState
import io.github.kurramkurram.angerlog.ui.component.text.AngerLogErrorText
import io.github.kurramkurram.angerlog.util.DateConverter
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import java.time.DayOfWeek
import kotlin.math.min

/**
 * 月を切り替えることのできるドラッグ幅.
 */
private const val CHANGE_PAGE_DRAG_AMOUNT = 50

/**
 * アイコンを表示できる数.
 */
private const val CAN_SHOW_ICON_COUNT = 3

@Serializable
object Calendar

/**
 * カレンダー画面.
 *
 * @param modifier [Modifier]
 * @param onItemClick 日付の選択した時の動作
 * @param viewModel カレンダー画面のViewModel
 */
@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    onItemClick: (Long, Long) -> Unit,
    viewModel: CalendarViewModel = koinViewModel(),
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val calendarState by viewModel.yearMonthState.collectAsStateWithLifecycle()

        AngerLogCalendarPicker(
            modifier = modifier.padding(bottom = 20.dp),
            state = calendarState,
            canShowBackArrow = viewModel.canShowBackArrow(),
            canShowNextArrow = viewModel.canShowNextArrow(),
            onMinusMonthClick = {
                viewModel.minusMonths()
                viewModel.updateAngerLogByMonth()
            },
            onPlusMonthClick = {
                viewModel.plusMonths()
                viewModel.updateAngerLogByMonth()
            },
            onShowYearDropDown = { viewModel.showYearDropDown() },
            onShowMonthDropDown = { viewModel.showMonthDropDown() },
            onCloseYearDropDown = { viewModel.closeYearDropDown() },
            onCloseMonthDropDown = { viewModel.closeMonthDropDown() },
            onSelectYear = {
                viewModel.selectedYear(it)
                viewModel.updateAngerLogByMonth()
            },
            onSelectMonth = {
                viewModel.selectedMonth(it)
                viewModel.updateAngerLogByMonth()
            },
        )

        AngerLogHorizontalDivider(
            modifier = modifier.padding(horizontal = 5.dp),
            color = MaterialTheme.colorScheme.primaryContainer,
        )

        val state by viewModel.state.collectAsStateWithLifecycle()
        when (state) {
            is CalendarUiState.Success -> {
                CalendarScreenCalendarView(
                    modifier = modifier,
                    viewModel = viewModel,
                    state = state as CalendarUiState.Success,
                    pickerUiState = calendarState,
                    onItemClick = onItemClick,
                )
            }

            is CalendarUiState.Loading -> {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = modifier.size(50.dp))
                }
            }

            is CalendarUiState.Error -> AngerLogErrorText()
        }
    }
}

/**
 * カレンダー画面のデータ取得後の画面.
 *
 * @param modifier [Modifier]
 * @param viewModel カレンダー画面のViewModel
 * @param state カレンダー画面の状態
 * @param pickerUiState 年月・ダイアログの表示状態
 * @param onItemClick 日付の選択した時の動作
 */
@Composable
fun CalendarScreenCalendarView(
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel,
    state: CalendarUiState.Success,
    pickerUiState: CalendarPickerUiState,
    onItemClick: (Long, Long) -> Unit,
) {
    Column(
        modifier =
            modifier.pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    if (dragAmount > CHANGE_PAGE_DRAG_AMOUNT && viewModel.canShowBackArrow()) {
                        viewModel.minusMonths()
                        viewModel.updateAngerLogByMonth()
                        return@detectHorizontalDragGestures
                    }
                    if (dragAmount < -CHANGE_PAGE_DRAG_AMOUNT && viewModel.canShowNextArrow()) {
                        viewModel.plusMonths()
                        viewModel.updateAngerLogByMonth()
                        return@detectHorizontalDragGestures
                    }
                }
            },
    ) {
        val firstDayOfWeek = pickerUiState.firstDayOfWeek
        val dayOfWeekSize = DayOfWeek.entries.size
        val yearMonth = pickerUiState.yearMonth
        LazyVerticalGrid(
            columns = GridCells.Fixed(dayOfWeekSize),
            modifier =
                modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(10.dp),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(dayOfWeekSize) { index ->
                val dayOfWeek = CustomDayOfWeek().select(index)
                CalendarScreenDayOWeekCard(dayOfWeek = dayOfWeek)
            }

            val calendarItemsList = state.calendarItemList
            items(pickerUiState.daysInMonth + firstDayOfWeek) { index ->
                val day = if (firstDayOfWeek > index) -1 else index - firstDayOfWeek + 1
                val isToday = day == state.today
                val calendarItems: CalendarIdListOfDayDto? =
                    if (day > 0 && calendarItemsList.isNotEmpty()) {
                        calendarItemsList[day - 1]
                    } else {
                        null
                    }
                CalendarScreenCalendarCard(
                    day = day,
                    isToday = isToday,
                    calendarItems = calendarItems,
                    onItemClick = { d ->
                        viewModel.selectDay(d)

                        val ids = state.calendarItemList[day - 1]?.ids
                        if (ids == null) {
                            val timeInMillis =
                                DateConverter.yearMonthDayToUnixTime(
                                    yearMonth.year,
                                    yearMonth.month.value - 1,
                                    day,
                                )
                            onItemClick(0, timeInMillis)
                            viewModel.clearDay()
                        }
                    },
                )
            }
        }

        if (state.showDialog) {
            val day = state.selectDay
            val timeInMillis =
                DateConverter.yearMonthDayToUnixTime(yearMonth.year, yearMonth.month.value - 1, day)
            val ids = state.calendarItemList[day - 1]?.ids
            if (ids != null) {
                CalendarScreenLogPicker(
                    day = day,
                    ids = ids,
                    onDismissRequest = { viewModel.clearDay() },
                ) { id, _ -> onItemClick(id, timeInMillis) }
            }
        }
    }
}

/**
 * カレンダー画面の曜日のカード.
 *
 * @param modifier [Modifier]
 * @param dayOfWeek 曜日（日から土）
 */
@Composable
fun CalendarScreenDayOWeekCard(
    modifier: Modifier = Modifier,
    dayOfWeek: CustomDayOfWeek.IDayOfWeek,
) {
    Text(
        modifier = modifier.padding(10.dp),
        text = dayOfWeek.getString(),
        color = dayOfWeek.getColor(),
        textAlign = TextAlign.Center,
    )
}

/**
 * カレンダー画面の日付のカード.
 *
 * @param modifier [Modifier]
 * @param day -1, 1~31（-1の時には空のカード）
 * @param onItemClick カードをクリックした時の動作
 */
@Composable
fun CalendarScreenCalendarCard(
    modifier: Modifier = Modifier,
    day: Int,
    isToday: Boolean = false,
    calendarItems: CalendarIdListOfDayDto?,
    onItemClick: (Int) -> Unit = {},
) {
    val isEmptyCell = day == -1
    val alpha = if (isEmptyCell) 0f else 1f
    val onClick: (Int) -> Unit =
        if (isEmptyCell) {
            {}
        } else {
            onItemClick
        }

    val ids = calendarItems?.ids
    Column(
        modifier =
            modifier
                .graphicsLayer { this.alpha = alpha }
                .clickable { onClick(day) }
                .border(
                    border =
                        BorderStroke(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primaryContainer,
                        ),
                    shape = MaterialTheme.shapes.small,
                )
                .background(
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = MaterialTheme.shapes.small,
                )
                .defaultMinSize(minHeight = 80.dp)
                .padding(vertical = 10.dp, horizontal = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "$day", color = if (isToday) Color.Blue else Color.Gray)

        if ((ids?.size ?: 0) > 0) {
            Row(
                modifier =
                    modifier
                        .fillMaxHeight()
                        .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ids?.let {
                    val iconCount = min(ids.size, CAN_SHOW_ICON_COUNT)
                    for (i in 0 until iconCount) {
                        Box(
                            modifier =
                                modifier
                                    .clip(CircleShape)
                                    .background(
                                        color = AngerLevel().select(ids[i].second).getColor(),
                                    )
                                    .padding(3.dp),
                        )
                    }
                    if (ids.size > CAN_SHOW_ICON_COUNT) {
                        Text(
                            stringResource(R.string.calendar_over_show_count),
                            modifier =
                                modifier
                                    .padding(1.dp),
                        )
                    }
                }
            }
        }
    }
}

/**
 * カレンダー画面の選択ダイアログ.
 * カレンダー画面で1つ以上、記録があるときに日付選択時に表示するダイアログ
 *
 * @param modifier [Modifier]
 * @param day 日にち
 * @param ids データベースの一意のidと怒りの強さ
 * @param onDismissRequest ダイアログ終了時の動作
 * @param onItemClick 日付 or 「＋」を押下した時の動作
 */
@Composable
fun CalendarScreenLogPicker(
    modifier: Modifier = Modifier,
    day: Int,
    ids: List<Pair<Long, Int>>,
    onDismissRequest: () -> Unit,
    onItemClick: (Long, Int) -> Unit,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier =
                modifier
                    .padding(50.dp)
                    .background(
                        color = MaterialTheme.colorScheme.onPrimary,
                        shape = MaterialTheme.shapes.small,
                    ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Spacer(Modifier.height(10.dp))

            Text(
                modifier = modifier.fillMaxWidth(),
                text = stringResource(R.string.calendar_log_picker_dialog_day, day),
                textAlign = TextAlign.Center,
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                items(1) {
                    CalendarScreenLogPickerCard(
                        modifier =
                            modifier.clickable {
                                onItemClick(0, day)
                                onDismissRequest()
                            },
                    ) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = stringResource(R.string.calendar_log_picker_add_cd),
                            modifier = modifier.padding(3.dp),
                        )
                    }
                }

                items(ids) {
                    CalendarScreenLogPickerCard(
                        modifier =
                            modifier.clickable {
                                onItemClick(it.first, day)
                                onDismissRequest()
                            },
                    ) {
                        Text(
                            modifier =
                                modifier
                                    .clip(CircleShape)
                                    .background(color = AngerLevel().select(it.second).getColor())
                                    .padding(horizontal = 10.dp, vertical = 3.dp),
                            text = "${it.second}",
                        )
                    }
                }
            }
        }
    }
}

/**
 * カレンダー画面の選択ダイアログの各項目.
 *
 * @param modifier [Modifier]
 * @param content コンテンツ
 */
@Composable
fun CalendarScreenLogPickerCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier =
            modifier
                .border(
                    border =
                        BorderStroke(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primaryContainer,
                        ),
                    shape = MaterialTheme.shapes.small,
                )
                .background(
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = MaterialTheme.shapes.small,
                )
                .padding(vertical = 10.dp),
    ) {
        content()
    }
}

// @Composable
// @Preview
// fun PreviewCalendarView() {
//    Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
//        CalendarScreenCalendarView(onItemClick = {})
//    }
// }
//
// @Composable
// @Preview
// fun PreviewCalendarCard() {
//    val calendarIdListOfDayDto =
//        CalendarIdListOfDayDto(1, listOf(Pair(1, 2), Pair(1, 3), Pair(1, 4), Pair(1, 5)))
//    CalendarScreenCalendarCard(day = 1, calendarItems = calendarIdListOfDayDto)
// }
