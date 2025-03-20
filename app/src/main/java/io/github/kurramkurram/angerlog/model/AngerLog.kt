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
    override fun equals(other: Any?): Boolean {
        if (other !is AngerLog) return false
        return date == other.date &&
            place == other.place &&
            latitude == other.latitude &&
            longitude == other.longitude &&
            event == other.event &&
            detail == other.detail &&
            thought == other.thought &&
            lookBackWhyAnger == other.lookBackWhyAnger &&
            lookBackAdvice == other.lookBackAdvice
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + level
        result = 31 * result + place.hashCode()
        result = 31 * result + latitude.hashCode()
        result = 31 * result + longitude.hashCode()
        result = 31 * result + event.hashCode()
        result = 31 * result + detail.hashCode()
        result = 31 * result + thought.hashCode()
        result = 31 * result + lookBackWhyAnger.hashCode()
        result = 31 * result + lookBackLevel
        result = 31 * result + lookBackAdvice.hashCode()
        return result
    }
}
