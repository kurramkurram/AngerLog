package io.github.kurramkurram.angerlog.ui.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.kurramkurram.angerlog.R

@Composable
fun AngerLogExpandableCard(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    isBottomRound: Boolean = false,
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ) {
                    expanded = !expanded
                }
                .background(
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = MaterialTheme.shapes.medium,
                ),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier =
                    modifier
                        .weight(1f)
                        .padding(horizontal = 20.dp, vertical = 20.dp),
                text = title,
            )

            Box(modifier = modifier.padding(horizontal = 5.dp)) {
                if (expanded) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowUp,
                        contentDescription =
                            stringResource(
                                R.string.expandable_card_close,
                            ),
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowDown,
                        contentDescription = stringResource(R.string.expandable_card_open),
                    )
                }
            }
        }

        if (expanded) {
            Text(
                modifier =
                    modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape =
                                if (isBottomRound) {
                                    RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                                } else {
                                    RoundedCornerShape(0.dp)
                                },
                        )
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                text = content,
            )
        }
    }
}
