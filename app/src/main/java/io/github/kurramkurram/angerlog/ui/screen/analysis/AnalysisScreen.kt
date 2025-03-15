package io.github.kurramkurram.angerlog.ui.screen.analysis

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.kurramkurram.angerlog.R
import io.github.kurramkurram.angerlog.ui.AngerLevel
import io.github.kurramkurram.angerlog.ui.AngerLevelType
import io.github.kurramkurram.angerlog.ui.CustomDayOfWeek
import io.github.kurramkurram.angerlog.ui.DayOfWeekType
import io.github.kurramkurram.angerlog.ui.component.AngerLogHorizontalDivider
import io.github.kurramkurram.angerlog.ui.component.calendarpicker.AngerLogCalendarPicker
import io.github.kurramkurram.angerlog.ui.component.card.AngerLogCounterCard
import io.github.kurramkurram.angerlog.ui.component.chart.line.AngerLogLineChart
import io.github.kurramkurram.angerlog.ui.component.chart.pie.AngerLogPieChart
import io.github.kurramkurram.angerlog.ui.component.chart.pie.PieData
import io.github.kurramkurram.angerlog.ui.component.chart.pie.PieItemDto
import io.github.kurramkurram.angerlog.ui.component.chart.bar.AngerLogBarChart
import io.github.kurramkurram.angerlog.ui.component.chart.bar.BarData
import io.github.kurramkurram.angerlog.ui.component.chart.bar.BarItemDto
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

private const val MAX_DAY_OF_MONTH = 31
private const val OFFSET_TOP_OF_LINE_CHART = 0.2f
private const val OFFSET_BOTTOM_OF_LINE_CHART = -0.2f

@Serializable
object Analysis

@Composable
fun AnalysisScreen(
    modifier: Modifier = Modifier,
    viewModel: AnalysisViewModel = koinViewModel(),
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val calendarState by viewModel.yearMonthState.collectAsStateWithLifecycle()
        val currentYearMonth = calendarState.yearMonth

        AngerLogCalendarPicker(
            modifier = modifier.padding(bottom = 20.dp),
            state = calendarState,
            canShowNextArrow = viewModel.canShowNextArrow(),
            selectYearMonth = currentYearMonth,
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
                viewModel.selectYear(it)
                viewModel.updateAngerLogByMonth()
            },
            onSelectMonth = {
                viewModel.selectMonth(it)
                viewModel.updateAngerLogByMonth()
            },
        )

        AngerLogHorizontalDivider(
            modifier = modifier.padding(horizontal = 5.dp),
            color = MaterialTheme.colorScheme.primaryContainer,
        )

        val state by viewModel.content.collectAsStateWithLifecycle()
        when (state) {
            is AnalysisUiState.Success -> {
                AnalysisScreenContent(
                    modifier = modifier,
                    state = state as AnalysisUiState.Success,
                )
            }

            is AnalysisUiState.Loading -> {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = modifier.size(50.dp))
                }
            }

            is AnalysisUiState.Error -> {}
        }
    }
}

@Composable
fun AnalysisScreenContent(
    modifier: Modifier = Modifier,
    state: AnalysisUiState.Success,
) {
    Column(
        modifier
            .padding(top = 20.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            AnalysisScreenElevatedCard(
                modifier = modifier.weight(1f),
                title = stringResource(R.string.analysis_record_count_title),
                description = stringResource(R.string.analysis_record_count_description),
            ) {
                AngerLogCounterCard(
                    modifier = modifier,
                    size = state.recordCount,
                    unit = stringResource(R.string.analysis_record_count_unit),
                )
            }
            AnalysisScreenElevatedCard(
                modifier = modifier.weight(1f),
                title = stringResource(R.string.analysis_look_back_count_title),
                description = stringResource(R.string.analysis_look_back_count_description),
            ) {
                AngerLogCounterCard(
                    modifier = modifier,
                    size = state.lookBackCount,
                    unit = stringResource(R.string.analysis_look_back_count_unit),
                )
            }
        }

        // スコアの割合 -> 円グラフ
        AnalysisScreenElevatedCard(
            title = stringResource(R.string.analysis_anger_rate_title),
            description = stringResource(R.string.analysis_anger_rate_description),
        ) {
            if (state.showRate) {
                val angerLevel = AngerLevel()
                val pieces = mutableListOf<PieItemDto>()
                AngerLevelType.entries.forEach {
                    val rate = state.rate[it]
                    val level = angerLevel.select(it)
                    pieces.add(
                        PieItemDto(
                            rate = rate ?: 0f,
                            label = level.getValue().toString(),
                            backgroundColor = level.getColor(),
                        ),
                    )
                }
                val pieData = PieData(pieItems = pieces)
                AngerLogPieChart(modifier = Modifier.size(150.dp), pieData = pieData)
            } else {
                AnalysisScreenLockedCard(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .size(150.dp),
                    message = stringResource(R.string.analysis_anger_rate_count_notice),
                )
            }
        }

        val barItem = mutableListOf<BarItemDto>()
        val customDayOfWeek = CustomDayOfWeek()
        DayOfWeekType.entries.forEachIndexed { index, dayOfWeek ->
            val iDayOfWeek = customDayOfWeek.select(dayOfWeek)
            barItem.add(
                BarItemDto(
                    size = state.averageOfDayOfWeek[index],
                    label = iDayOfWeek.getString(),
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                ),
            )
        }
        val barData = BarData(barItem)

        // 曜日別の平均　-> 棒グラフ
        AnalysisScreenElevatedCard(
            title = stringResource(R.string.analysis_average_per_day_of_week_title),
            description = stringResource(R.string.analysis_average_per_day_of_week_description),
        ) {
            if (state.showAverageOfDayWeek) {
                AngerLogBarChart(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .size(150.dp),
                    data = barData,
                    animationEasing = FastOutSlowInEasing,
                )
            } else {
                AnalysisScreenLockedCard(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .size(150.dp),
                    message = stringResource(R.string.analysis_average_per_day_of_week_notice),
                )
            }
        }

        // 月の変遷 -> 折れ線グラフ
        AnalysisScreenElevatedCard(
            title = stringResource(R.string.analysis_transition_title),
            description = stringResource(R.string.analysis_transition_description),
        ) {
            if (state.showTransition) {
                val angerLevel = AngerLevel()
                // 上下に余白を持たせるためにOFFSET_TOP_OF_LINE_CHART/OFFSET_BOTTOM_OF_LINE_CHART分オフセットを追加する
                AngerLogLineChart(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .size(150.dp),
                    lineData = state.transition,
                    maxX = angerLevel.getMaxLevel() + OFFSET_TOP_OF_LINE_CHART,
                    minX = angerLevel.getMinLevel() + OFFSET_BOTTOM_OF_LINE_CHART,
                    maxY = MAX_DAY_OF_MONTH,
                    lineColor = MaterialTheme.colorScheme.primaryContainer,
                )
            } else {
                AnalysisScreenLockedCard(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .size(150.dp),
                    message =
                        stringResource(
                            R.string.analysis_transition_notice,
                            3 - state.dataCount,
                        ),
                )
            }
        }
    }
}

@Composable
fun AnalysisScreenElevatedCard(
    modifier: Modifier = Modifier,
    title: String = "",
    description: String = "",
    content: @Composable () -> Unit,
) {
    ElevatedCard(
        modifier =
            modifier
                .padding(10.dp),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 6.dp,
            ),
    ) {
        Column(
            modifier =
                Modifier
                    .background(color = MaterialTheme.colorScheme.onPrimary)
                    .fillMaxSize()
                    .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // タイトル
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            // 説明
            Text(text = description, style = MaterialTheme.typography.titleSmall)

            content()
        }
    }
}

@Composable
fun AnalysisScreenLockedCard(
    modifier: Modifier = Modifier,
    message: String,
) {
    Box(
        modifier =
            modifier
                .padding(10.dp)
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = MaterialTheme.shapes.small,
                ),
        contentAlignment = Alignment.Center,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = stringResource(R.string.analysis_lock_cd),
            )
            Spacer(Modifier.width(10.dp))
            Text(text = message)
        }
    }
}
