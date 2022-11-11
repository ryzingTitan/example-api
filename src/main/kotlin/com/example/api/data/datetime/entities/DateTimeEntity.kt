package com.example.api.data.datetime.entities

import com.fasterxml.jackson.annotation.JsonAlias
import lombok.Generated

@Generated
data class DateTimeEntity(
    val abbreviation: String,
    @JsonAlias("client_ip") val clientIp: String,
    @JsonAlias("datetime") val dateTime: String,
    @JsonAlias("day_of_week") val dayOfWeek: Int,
    @JsonAlias("day_of_year") val dayOfYear: Int,
    val dst: Boolean,
    @JsonAlias("dst_from") val dstFrom: String,
    @JsonAlias("dst_offset") val dstOffset: Int,
    @JsonAlias("dst_until") val dstUntil: String,
    @JsonAlias("raw_offset") val rawOffset: Int,
    val timezone: String,
    @JsonAlias("unixtime") val unixTime: Long,
    @JsonAlias("utc_datetime") val utcDateTime: String,
    @JsonAlias("utc_offset") val utcOffset: String,
    @JsonAlias("week_number") val weekNumber: Int,
)
