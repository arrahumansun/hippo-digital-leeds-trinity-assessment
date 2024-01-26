package com.food.outlet.leedstrinityassessmenttask.service

import com.food.outlet.leedstrinityassessmenttask.config.ErrorResponse
import com.food.outlet.leedstrinityassessmenttask.config.NoDataWithCodeFoundException
import com.food.outlet.leedstrinityassessmenttask.data.OutletsDataDTO
import com.food.outlet.leedstrinityassessmenttask.data.OutletsList
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service
import com.google.gson.Gson
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import java.nio.charset.Charset


@Service
class OutletsDataService(
    var resourceLoader: ResourceLoader
) {


    fun getAllOutletsData(
        pageNumber: Int,
        pageSize: Int,
    ): OutletsList {
            val gson = Gson()

        if (pageNumber < 0 || pageSize <= 0) {
            throw NoDataWithCodeFoundException(
                "Data",
                "Page $pageNumber and Size $pageSize",
            )
        }

            val res = resourceLoader.getResource("classpath:outlets.json")
            var jsonString = res.getContentAsString(Charset.defaultCharset())
            val outlets =  gson.fromJson(jsonString, Array<OutletsDataDTO>::class.java)
            val fullList = outlets.toList()
        val startIndex = (pageNumber * pageSize)
        if (startIndex >= fullList.size) {
            throw NoDataWithCodeFoundException(
                "Data",
                "Page $pageNumber",
            )
        }

        val endIndex = (pageNumber * pageSize) + (pageSize)
        if (startIndex < endIndex && endIndex <= fullList.size) {
            val pList = fullList.subList(startIndex, endIndex)
            return OutletsList(pList, pList.toList().size, pageNumber, fullList.size, (endIndex == fullList.size))
        } else if (startIndex < endIndex) {
            val pList = fullList.subList(startIndex, fullList.size)
            return OutletsList(pList, pList.toList().size, pageNumber, fullList.size, true)
        }
        return OutletsList(emptyList(), 0, 0 ,0, false)
    }
}