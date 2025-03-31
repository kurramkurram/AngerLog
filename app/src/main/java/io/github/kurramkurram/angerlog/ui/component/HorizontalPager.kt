package io.github.kurramkurram.angerlog.ui.component

import androidx.compose.foundation.gestures.TargetedFlingBehavior
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * 横方向のページャー.
 *
 * @param modifier [Modifier]
 * @param state ページの状態
 * @param findBehavior 切り替え動作の検知
 * @param pageContent 該当のページのコンテンツ
 */
@Composable
fun AngerLogHorizontalPager(
    modifier: Modifier = Modifier,
    state: PagerState,
    findBehavior: TargetedFlingBehavior = PagerDefaults.flingBehavior(
        state,
        snapPositionalThreshold = 0.2f
    ),
    pageContent: @Composable (Int) -> Unit
) {
    HorizontalPager(
        modifier = modifier,
        state = state,
        flingBehavior = findBehavior,
    ) { page ->
        pageContent(page)
    }
}