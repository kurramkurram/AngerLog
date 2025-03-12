package io.github.kurramkurram.angerlog.ui.screen.tips

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.kurramkurram.angerlog.R
import io.github.kurramkurram.angerlog.ui.component.card.AngerLogExpandableCard
import io.github.kurramkurram.angerlog.ui.component.layout.AngerLogBackButtonLayout
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object Tips

@Composable
fun TipsScreen(
    modifier: Modifier = Modifier,
    viewModel: TipsInfoViewModel = koinViewModel(),
    onClickBack: () -> Unit
) {
    AngerLogBackButtonLayout(
        modifier = modifier,
        onClickBack = onClickBack,
        title = stringResource(R.string.tips),
        description = stringResource(R.string.tips_description)
    ) {
        val data = viewModel.getTips()
        LazyColumn(
            modifier = modifier
                .fillMaxHeight()
                .padding(horizontal = 10.dp)
        ) {
            items(data) { tipsInfoCategory ->
                Text(
                    modifier = modifier.padding(top = 10.dp, bottom = 10.dp),
                    text = tipsInfoCategory.category,
                    style = MaterialTheme.typography.titleMedium
                )
                Column(
                    modifier = modifier.background(
                        color = MaterialTheme.colorScheme.onPrimary,
                        shape = MaterialTheme.shapes.medium
                    )
                ) {
                    tipsInfoCategory.info.forEachIndexed { index, tipsInfo ->
                        val isLast = index == tipsInfoCategory.info.size - 1
                        AngerLogExpandableCard(
                            modifier = modifier,
                            title = tipsInfo.title,
                            content = tipsInfo.content,
                            isBottomRound = isLast
                        )
                    }
                }
            }
        }
    }
}
