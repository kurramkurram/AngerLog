package io.github.kurramkurram.angerlog.ui.screen.tips

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Apps
import androidx.compose.material.icons.outlined.Mood
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.kurramkurram.angerlog.R
import io.github.kurramkurram.angerlog.data.repository.TipsRepository
import kotlinx.coroutines.launch

/**
 * お役立ちTips画面のViewModel.
 *
 * @param tipsRepository Tips表示状態のRepository
 */
class TipsInfoViewModel(private val tipsRepository: TipsRepository) : ViewModel() {

    /**
     * Tipsの表示したことを表す.
     *
     * @param context [Context]
     */
    fun hasShowTips(context: Context) {
        viewModelScope.launch { tipsRepository.hasShowUnreadTips(context) }
    }

    /**
     * Tips情報を取得する.
     *
     * @return お役立ちTips
     */
    @Composable
    fun getTips(): List<TipsInfoCategoryDto> {
        return listOf(
            TipsInfoCategoryDto(
                icon = Icons.Outlined.Mood,
                category = stringResource(R.string.tips_category_change_of_pace),
                info =
                    listOf(
                        TipsInfoDto(
                            title = stringResource(R.string.tips_category_change_of_pace_listen_music_title),
                            content = stringResource(R.string.tips_category_change_of_pace_listen_music_description),
                        ),
                        TipsInfoDto(
                            title = stringResource(R.string.tips_category_change_of_pace_count_6_seconds_title),
                            content = stringResource(R.string.tips_category_change_of_pace_count_6_seconds_description),
                        ),
                        TipsInfoDto(
                            title = stringResource(R.string.tips_category_change_of_pace_change_place_title),
                            content = stringResource(R.string.tips_category_change_of_pace_change_place_description),
                        ),
                        TipsInfoDto(
                            title = stringResource(R.string.tips_category_change_of_pace_think_other_things_title),
                            content = stringResource(R.string.tips_category_change_of_pace_think_other_things_description),
                        ),
                    ),
            ),
            TipsInfoCategoryDto(
                icon = Icons.Outlined.Apps,
                category = stringResource(R.string.tips_category_use_app),
                info =
                    listOf(
                        TipsInfoDto(
                            title = stringResource(R.string.tips_category_use_app_record_log_title),
                            content = stringResource(R.string.tips_category_use_app_record_log_description),
                        ),
                        TipsInfoDto(
                            title = stringResource(R.string.tips_category_use_app_look_back_title),
                            content = stringResource(R.string.tips_category_use_app_look_back_description),
                        ),
                        TipsInfoDto(
                            title = stringResource(R.string.tips_category_use_app_check_analysis_title),
                            content = stringResource(R.string.tips_category_use_app_check_analysis_description),
                        ),
                    ),
            ),
            TipsInfoCategoryDto(
                icon = Icons.Outlined.Psychology,
                category = stringResource(R.string.tips_category_thinking),
                info =
                    listOf(
                        TipsInfoDto(
                            title = stringResource(R.string.tips_category_thinking_put_yourself_in_someone_title),
                            content = stringResource(R.string.tips_category_thinking_put_yourself_in_someone_description),
                        ),
                    ),
            ),
        )
    }
}
