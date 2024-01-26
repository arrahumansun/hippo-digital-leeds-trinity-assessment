package com.food.outlet.leedstrinityassessmenttask.data

import java.time.LocalDateTime

data class OutletsDataDTO(

    var id: Int,

    val name: String,

    val openTime: String,

    val closeTime : String,

    val active : Boolean
)

data class OutletsList(
    val content: List<OutletsDataDTO>?,
    val pageSize: Int?,
    val page: Int?,
    val totalElements: Int?,
    val last: Boolean,
)