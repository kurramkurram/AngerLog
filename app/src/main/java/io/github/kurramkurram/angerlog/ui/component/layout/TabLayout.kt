package io.github.kurramkurram.angerlog.ui.component.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.kurramkurram.angerlog.ui.component.AngerLogHorizontalPager

/**
 * タブレイアウト.
 *
 * @param modifier [Modifier]
 * @param pagerState [AngerLogHorizontalPager]のページの状態
 * @param list タブで表示するコンテンツのリスト
 * @param selectedIndex タブの選択された番号
 * @param onTabSelected タブが選択されたときの動作
 */
@Composable
fun AngerLogTabLayout(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    list: List<TabContent>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
) {
    Column {
        TabRow(
            selectedTabIndex = selectedIndex,
            indicator = { tabPositions ->
                if (selectedIndex < tabPositions.size) {
                    Box(
                        Modifier
                            .tabIndicatorOffset(tabPositions[selectedIndex])
                            .padding(horizontal = 20.dp)
                            .height(3.dp)
                            .background(
                                color = MaterialTheme.colorScheme.onSurface,
                                shape = RoundedCornerShape(100.dp, 100.dp, 0.dp, 0.dp),
                            ),
                    )
                }
            },
        ) {
            list.forEachIndexed { index, content ->
                AngerLogTab(
                    modifier = modifier,
                    selected = selectedIndex == index,
                    label = content.label,
                ) { onTabSelected(index) }
            }
        }

        AngerLogHorizontalPager(
            modifier = modifier,
            state = pagerState,
        ) { page ->
            list[page].content()
        }
    }
}

/**
 * タブのアイテム.
 *
 * @param modifier [Modifier]
 * @param selected 選択されているかどうか
 * @param label タブのラベル
 * @param onClick タブが押下されたときの動作
 */
@Composable
fun AngerLogTab(
    modifier: Modifier = Modifier,
    selected: Boolean,
    label: String,
    onClick: () -> Unit,
) {
    Tab(modifier = modifier, selected = selected, onClick = onClick) {
        Text(
            modifier = modifier.padding(vertical = 10.dp),
            text = label,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
        )
    }
}

/**
 * タブに表示するコンテンツ.
 *
 * @param label ラベル
 * @param content コンテンツ
 */
class TabContent(val label: String, val content: @Composable () -> Unit)
