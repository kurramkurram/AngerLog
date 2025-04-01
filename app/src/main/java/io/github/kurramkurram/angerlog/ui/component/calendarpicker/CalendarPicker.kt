package io.github.kurramkurram.angerlog.ui.component.calendarpicker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.kurramkurram.angerlog.R
import io.github.kurramkurram.angerlog.ui.component.AngerLogDropDown

/**
 * 年の最小値.
 */
const val DEFAULT_MIN_YEAR_OF_PICKER = 2023

/**
 * 月日を表示・変更する.
 *
 * @param modifier [Modifier]
 * @param minYear 年の最小値
 * @param canShowBackArrow 「＜」の表示可否
 * @param canShowNextArrow 「＞」の表示可否
 * @param state 年月・ダイアログの表示状態
 * @param onMinusMonthClick 「＜」を押下した時の動作
 * @param onPlusMonthClick 「＞」を押下した時の動作
 * @param onShowYearDropDown 「年」のドロップダウンを表示した時の動作
 * @param onShowMonthDropDown 「月」のドロップダウンを表示した時の動作
 * @param onCloseYearDropDown 「年」のドロップダウンを閉じた時の動作
 * @param onCloseMonthDropDown 「月」のドロップダウンを閉じた時の動作
 * @param onSelectYear 「年」をドロップダウンで選択した時の動作
 * @param onSelectMonth 「月」をドロップダウンで選択した時の動作
 */
@Composable
fun AngerLogCalendarPicker(
    modifier: Modifier = Modifier,
    minYear: Int = DEFAULT_MIN_YEAR_OF_PICKER,
    canShowBackArrow: Boolean,
    canShowNextArrow: Boolean,
    state: CalendarPickerUiState,
    onMinusMonthClick: () -> Unit,
    onPlusMonthClick: () -> Unit,
    onShowYearDropDown: () -> Unit,
    onShowMonthDropDown: () -> Unit,
    onCloseYearDropDown: () -> Unit,
    onCloseMonthDropDown: () -> Unit,
    onSelectYear: (year: Int) -> Unit,
    onSelectMonth: (month: Int) -> Unit,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            modifier =
                Modifier
                    .alpha(if (canShowBackArrow) 1f else 0f)
                    .clickable(enabled = canShowBackArrow) { onMinusMonthClick() },
            contentDescription = stringResource(R.string.calendar_title_back_cd),
        )

        Row(
            modifier =
                Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Column {
                Text(
                    text = stringResource(R.string.calendar_title_year, state.yearMonth.year),
                    modifier =
                        Modifier
                            .clickable { onShowYearDropDown() }
                            .padding(horizontal = 10.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                )

                AngerLogDropDown(
                    min = minYear,
                    max = state.dropDownMaxYear,
                    expanded = state.yearDropDown,
                    onDismissRequest = { onCloseYearDropDown() },
                ) { onSelectYear(it) }
            }

            Column {
                Text(
                    text =
                        stringResource(
                            R.string.calendar_title_month,
                            state.yearMonth.month.value,
                        ),
                    modifier =
                        Modifier
                            .clickable { onShowMonthDropDown() }
                            .padding(horizontal = 10.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                )
                AngerLogDropDown(
                    min = 1,
                    max = state.dropDownMaxMonth,
                    expanded = state.monthDropDown,
                    onDismissRequest = { onCloseMonthDropDown() },
                ) { onSelectMonth(it) }
            }
        }

        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowRight,
            modifier =
                Modifier
                    .alpha(if (canShowNextArrow) 1f else 0f)
                    .clickable(enabled = canShowNextArrow) { onPlusMonthClick() },
            contentDescription = stringResource(R.string.calendar_title_next_cd),
        )
    }
}
