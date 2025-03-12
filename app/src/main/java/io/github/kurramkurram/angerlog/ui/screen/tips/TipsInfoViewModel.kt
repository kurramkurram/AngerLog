package io.github.kurramkurram.angerlog.ui.screen.tips

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import io.github.kurramkurram.angerlog.R

class TipsInfoViewModel : ViewModel() {

    @Composable
    fun getTips(): List<TipsInfoCategoryDto> {
        return listOf(
            TipsInfoCategoryDto(
                category = stringResource(R.string.tips_category_change_of_pace),
                info = listOf(
                    TipsInfoDto(
                        title = stringResource(R.string.tips_category_change_of_pace_listen_music_title),
                        content = stringResource(R.string.tips_category_change_of_pace_listen_music_description)
                    ),
                    TipsInfoDto(
                        title = stringResource(R.string.tips_category_change_of_pace_count_6_seconds_title),
                        content = stringResource(R.string.tips_category_change_of_pace_count_6_seconds_description)
                    )
                )
            ),
            TipsInfoCategoryDto(
                category = stringResource(R.string.tips_category_use_app),
                info = listOf(
                    TipsInfoDto(
                        title = stringResource(R.string.tips_category_use_app_record_log_title),
                        content = stringResource(R.string.tips_category_use_app_record_log_description)
                    ),
                    TipsInfoDto(
                        title = stringResource(R.string.tips_category_use_app_look_back_title),
                        content = stringResource(R.string.tips_category_use_app_look_back_description)
                    )
                )
            ),
            TipsInfoCategoryDto(
                category = stringResource(R.string.tips_category_thinking),
                info = listOf(
                    TipsInfoDto(
                        title = stringResource(R.string.tips_category_thinking_put_yourself_in_someone_title),
                        content = stringResource(R.string.tips_category_thinking_put_yourself_in_someone_description)
                    )
                )
            )
        )
    }
}