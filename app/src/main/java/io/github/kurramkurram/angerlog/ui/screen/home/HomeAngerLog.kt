package io.github.kurramkurram.angerlog.ui.screen.home

import io.github.kurramkurram.angerlog.model.AngerLog
import io.github.kurramkurram.angerlog.model.ShowLookBack
import io.github.kurramkurram.angerlog.util.DateConverter.Companion.dateToString

/**
 * ホーム画面に表示する怒りの記録.
 *
 * @param angerLog 怒りの記録
 * @param now 現在の日時
 */
class HomeAngerLog(
    val angerLog: AngerLog,
    now: Long,
) {
    private val showLookBackIcon = ShowLookBack(logDate = angerLog.date.time, now = now)

    /**
     * データベースの一意のidを取得する
     *
     * @return id
     */
    fun getId(): Long = angerLog.id

    /**
     * 日付を取得する.
     *
     * @return 日付
     */
    fun getDate(): String = angerLog.date.dateToString()

    /**
     * できごとを取得する.
     *
     * @return できごと
     */
    fun getEvent(): String = angerLog.event

    /**
     * 怒りの強さを取得する.
     *
     * @return 怒りの強さ
     */
    fun getLevel(): Int = angerLog.level

    /**
     * 振り返りのアイコンを表示するかを判定する.
     *
     * @return true: 振り返り可能
     */
    fun canShowLookBack(): Boolean = showLookBackIcon.showLookBack
}
