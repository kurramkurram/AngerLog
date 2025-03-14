// package io.github.kurramkurram.angerlog.ui.screen
//
// import androidx.compose.foundation.BorderStroke
// import androidx.compose.foundation.border
// import androidx.compose.foundation.layout.Column
// import androidx.compose.foundation.layout.fillMaxHeight
// import androidx.compose.foundation.layout.padding
// import androidx.compose.foundation.lazy.LazyColumn
// import androidx.compose.foundation.shape.RoundedCornerShape
// import androidx.compose.material3.MaterialTheme
// import androidx.compose.material3.Text
// import androidx.compose.runtime.Composable
// import androidx.compose.ui.Modifier
// import androidx.compose.ui.unit.dp
// import io.github.kurramkurram.angerlog.model.AngerLog
// import io.github.kurramkurram.angerlog.ui.component.AngerLogHorizontalDivider
// import io.github.kurramkurram.angerlog.ui.component.AngerLogListItem
// import io.github.kurramkurram.angerlog.ui.component.layout.AngerLogBackButtonLayout
// import kotlinx.serialization.Serializable
//
// @Serializable
// object LookBack
//
// @Composable
// fun LookBackScreen(
//    modifier: Modifier = Modifier,
//    onClickBack: () -> Unit,
//    onItemClick: (id: Long) -> Unit
// ) {
//    Column(modifier = modifier) {
//        AngerLogBackButtonLayout(
//            onClickBack = onClickBack,
//            title = "振り返り"
//        ) {
//            // 期間
//            Text(modifier = modifier, text = "2025/1/1/～2025/1/31")
//
//            // 件数・平均
//
//            val angerLogList = listOf(
//                AngerLog(
//                    id = 0,
//                    date = "2025/01/16",
//                    time = "23:08",
//                    level = 2,
//                    place = "駅",
//                    event = "新人が会議中にメモを取っていなかった",
//                    thought = "会議中はメモをとるべき",
//                ),
//                AngerLog(
//                    id = 1,
//                    date = "2025/01/17",
//                    time = "23:10",
//                    level = 5,
//                    place = "",
//                    event = "歩きスマホをしている人にぶつかられた",
//                    thought = "イラッとした",
//                ),
//                AngerLog(
//                    id = 2,
//                    date = "2025/01/20",
//                    time = "23:10",
//                    level = 5,
//                    place = "",
//                    event = "AAAAAAAAA",
//                    thought = "イラッとした",
//                )
//            )
//
//            // リスト -> 振り返りの個別画面に遷移する
//            LazyColumn(
//                modifier = modifier
//                    .padding(10.dp)
//                    .fillMaxHeight()
//                    .border(
//                        border = BorderStroke(
//                            width = 1.dp,
//                            color = MaterialTheme.colorScheme.primaryContainer
//                        ), shape = MaterialTheme.shapes.small
//                    )
//            ) {
//                items(angerLogList.size) { index ->
//                    AngerLogListItem(
//                        item = angerLogList[index],
//                        onItemClick = { onItemClick(it) },
//                        isRoundTop = index == 0,
//                        isRoundBottom = index == angerLogList.size - 1
//                    )
//                    AngerLogHorizontalDivider()
//                }
//            }
//        }
//    }
// }
//
// //@Preview
// //@Composable
// //fun PreviewLookBackScreen() {
// //    LookBackScreen(onClickButton = {}) { }
// //}
