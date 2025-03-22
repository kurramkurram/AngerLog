package io.github.kurramkurram.angerlog.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * 怒りの記録データ.
 * データベースにもこの構成のテーブルで保存される.
 *
 * @param id 一意のid
 * @param date 日付
 * @param level 怒りの強さ
 * @param place 場所
 * @param latitude 緯度（今は使わない）
 * @param longitude 経度（今は使わない）
 * @param event できごと
 * @param detail できごとの詳細
 * @param thought 思ったこと
 * @param lookBackWhyAnger 振り返りのなぜ怒りを感じたか
 * @param lookBackLevel 振り返りの怒りの強さの
 * @param lookBackAdvice 振り返りのアドバイス
 */
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
    val lookBackWhyAnger: String = "",
    val lookBackLevel: Int = 0,
    val lookBackAdvice: String = "",
) {
    /**
     * 同一判定.
     * 怒りの強さ([level], [lookBackLevel])以外の項目において値が一致していれば、同一と判定する.
     *
     * @param other 比較対象の怒りの記録データ.
     * @return true: 同一の怒りのデータ
     */
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

    /**
     * Hash値の生成
     *
     * @return Hash値
     */
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
