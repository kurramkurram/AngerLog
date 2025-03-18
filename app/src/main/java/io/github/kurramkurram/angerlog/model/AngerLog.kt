package io.github.kurramkurram.angerlog.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "t_anger_log")
class AngerLog(
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
    val lookBackWhyAnger: String = "",
    // 振り返りの怒りレベル
    val lookBackLevel: Int = 0,
    // 振り返りアドバイス
    val lookBackAdvice: String = "",
) {
    fun notEquals(other: AngerLog): Boolean =
        date != other.date ||
            place != other.place ||
            latitude != other.latitude ||
            longitude != other.longitude ||
            event != other.event ||
            detail != other.detail ||
            thought != other.thought ||
            lookBackWhyAnger != other.lookBackWhyAnger ||
            lookBackAdvice != other.lookBackAdvice
}
