package com.test.wheatherapp.data

import com.test.wheatherapp.utils.Utils

data class WeatherItemData(
    val id: String,
    val imageId: Int,
    val name: String,
    val sender: String,
    val startDate: Long,
    val endDate: Long,
) {

    constructor(id: String, imageId: Int, name: String, sender: String, startDate: String?, endDate: String?)
            : this(id, imageId, name, sender, Utils.convertDateToTimestamp(startDate), Utils.convertDateToTimestamp(endDate) )

    private val started: String = Utils.convertTimestampToDate(startDate)
    private val ended: String = Utils.convertTimestampToDate(endDate)
    private val duration: String = Utils.reformatDuration(endDate - startDate)

    fun getStartedString() = started
    fun getEndedString() = ended
    fun getDurationString() = duration
}
