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
import java.time.YearMonth

private const val DEFAULT_MIN_YEAR_OF_PICKER = 2023

@Composable
fun AngerLogCalendarPicker(
    modifier: Modifier = Modifier,
    minYear: Int = DEFAULT_MIN_YEAR_OF_PICKER,
    canShowNextArrow: Boolean,
    selectYearMonth: YearMonth,
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
            modifier = Modifier.clickable { onMinusMonthClick() },
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
                    text = stringResource(R.string.calendar_title_year, selectYearMonth.year),
                    modifier =
                        Modifier
                            .clickable { onShowYearDropDown() }
                            .padding(horizontal = 10.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                )

                AngerLogDropDown(
                    min = minYear,
                    max = state.now.year,
                    expanded = state.yearDropDown,
                    onDismissRequest = { onCloseYearDropDown() },
                ) { onSelectYear(it) }
            }

            Column {
                Text(
                    text =
                        stringResource(
                            R.string.calendar_title_month,
                            selectYearMonth.month.value,
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
                    max = 12,
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
