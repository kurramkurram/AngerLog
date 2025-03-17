package io.github.kurramkurram.angerlog.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Reviews
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.github.kurramkurram.angerlog.ui.AngerLevel

@Composable
fun HomeListItem(
    modifier: Modifier = Modifier,
    item: HomeAngerLogDto,
    onItemClick: (id: Long) -> Unit,
    isRoundTop: Boolean = true,
    isRoundBottom: Boolean = true,
) {
    ElevatedCard(
        modifier =
            modifier
                .padding(10.dp)
                .clickable { onItemClick(item.getId()) },
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 6.dp,
            ),
    ) {
        Column {
            val shape =
                if (isRoundTop && isRoundBottom) {
                    MaterialTheme.shapes.small
                } else if (isRoundTop) {
                    RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                } else if (isRoundBottom) {
                    RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                } else {
                    RoundedCornerShape(0.dp)
                }
            Row(
                modifier =
                    modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.onPrimary, shape = shape)
                        .padding(horizontal = 15.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = modifier.weight(1f)) {
                    Text(text = item.getDate())
                    Text(text = item.getEvent(), maxLines = 1, overflow = TextOverflow.Ellipsis)
                }

                if (item.canShowLookBack()) {
                    Icon(
                        modifier = modifier.padding(horizontal = 20.dp),
                        imageVector = Icons.Outlined.Reviews,
                        contentDescription = "振り返りができます",
                    )
                }

                Text(
                    modifier =
                        modifier
                            .clip(CircleShape)
                            .background(color = AngerLevel().select(level = item.getLevel()).getColor())
                            .padding(horizontal = 20.dp, vertical = 5.dp),
                    text = "${item.getLevel()}",
                )
            }
        }
    }
}
