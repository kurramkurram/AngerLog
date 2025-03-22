package io.github.kurramkurram.angerlog.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.kurramkurram.angerlog.R
import io.github.kurramkurram.angerlog.model.Time
import java.text.SimpleDateFormat
import java.util.Date

/**
 * 日付と時間を縦に並べて表示する.
 *
 * @param modifier [Modifier]
 * @param date 年月日
 * @param time 時間
 * @param onDateClick 年月日を押下した時の動作
 * @param onTimeClick 時間を押下した時の動作
 */
@SuppressLint("SimpleDateFormat", "DefaultLocale")
@Composable
fun AngerLogVerticalDate(
    modifier: Modifier = Modifier,
    date: Date = Date(),
    time: Time,
    onDateClick: () -> Unit = {},
    onTimeClick: () -> Unit = {},
) {
    val dateSdf = SimpleDateFormat("yyyy年MM月dd日")
    val dateNow = dateSdf.format(date)

    Column(
        modifier =
        modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Outlined.DateRange,
                contentDescription = stringResource(R.string.date_date_cd)
            )
            Spacer(Modifier.padding(2.dp))
            Text(modifier = modifier.clickable { onDateClick() }, text = dateNow)
        }

        Spacer(Modifier.padding(5.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Outlined.Schedule,
                contentDescription = stringResource(R.string.date_time_cd)
            )
            Spacer(Modifier.padding(2.dp))
            Text(modifier = modifier.clickable { onTimeClick() }, text = time.time)
        }
    }
}

// @Preview
// @Composable
// fun PreViewAngerLogVerticalDate() {
//    AngerLogVerticalDate(date = Date(170000000), time = Time(1, 1))
// }
