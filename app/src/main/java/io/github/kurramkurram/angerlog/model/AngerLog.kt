package io.github.kurramkurram.angerlog.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "t_anger_log")
data class AngerLog(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val date: Date = Date(),
    val level: Int = 0,
    val place: String = "",
    val latitude: Float = 0f,
    val longitude: Float = 0f,
    val event: String = "",
    val detail: String = "",
    val thought: String = "",
    // 振り返りべき
    val lookBackShould: String = "",
    // 振り返りの怒りレベル
    val lookBackLevel: Int = 0,
    // 振り返りアドバイス
    val lookBackAdvice: String = "",
)
