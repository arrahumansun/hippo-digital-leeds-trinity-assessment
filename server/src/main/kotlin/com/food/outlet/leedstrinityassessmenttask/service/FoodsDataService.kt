package com.food.outlet.leedstrinityassessmenttask.service

import com.food.outlet.leedstrinityassessmenttask.config.NoDataWithCodeFoundException
import com.food.outlet.leedstrinityassessmenttask.data.FoodsDataDTO
import com.food.outlet.leedstrinityassessmenttask.data.FoodsList
import com.food.outlet.leedstrinityassessmenttask.data.OutletsDataDTO
import com.food.outlet.leedstrinityassessmenttask.data.OutletsList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service
import com.google.gson.Gson
import java.nio.charset.Charset


@Service
class FoodsDataService(
    var resourceLoader: ResourceLoader
) {

    fun getAllFoodsMenuData(
        pageNumber: Int,
        pageSize: Int,
    ): FoodsList {
        val gson = Gson()

        if (pageNumber < 0 || pageSize <= 0) {
            throw NoDataWithCodeFoundException(
                "Data",
                "Page $pageNumber and Size $pageSize",
            )
        }
        val res = resourceLoader.getResource("classpath:foods.json")
        var jsonString = res.getContentAsString(Charset.defaultCharset())
        val foods =  gson.fromJson(jsonString, Array<FoodsDataDTO>::class.java)
        val fullList = foods.toList()
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
            return FoodsList(pList, pList.toList().size, pageNumber, fullList.size, (endIndex == fullList.size))
        } else if (startIndex < endIndex) {
            val pList = fullList.subList(startIndex, fullList.size)
            return FoodsList(pList, pList.toList().size, pageNumber, fullList.size, true)
        }
        return FoodsList(emptyList(), 0, 0 ,0, false)

    }

    fun getOutletFoodsMenuData(
        outletId: Int,
        pageNumber: Int,
        pageSize: Int,
    ): FoodsList {
        val gson = Gson()

        if (pageNumber < 0 || pageSize < 0) {
            throw NoDataWithCodeFoundException(
                "Data",
                "Page $pageNumber and Size $pageSize",
            )
        }
        val res = resourceLoader.getResource("classpath:foods.json")
        var jsonString = res.getContentAsString(Charset.defaultCharset())
        val foods =  gson.fromJson(jsonString, Array<FoodsDataDTO>::class.java)
        val fullList = foods.toList()
        val specificOutletsFoodList = mutableListOf<FoodsDataDTO>()

        fullList.forEach {
                if (it.outletId == outletId){
                    specificOutletsFoodList.add(it)
                }
        }
        if (pageNumber == 0 && pageSize == 0){
            val pList = specificOutletsFoodList
            return FoodsList(pList, pList.toList().size, pageNumber, specificOutletsFoodList.size, true)
        }
        val startIndex = (pageNumber * pageSize)
        if (startIndex >= specificOutletsFoodList.size) {
            throw NoDataWithCodeFoundException(
                "Data",
                "Page $pageNumber",
            )
        }

        val endIndex = (pageNumber * pageSize) + (pageSize)
        if (startIndex < endIndex && endIndex <= fullList.size) {
            val pList = specificOutletsFoodList.subList(startIndex, endIndex)
            return FoodsList(pList, pList.toList().size, pageNumber, specificOutletsFoodList.size, (endIndex == specificOutletsFoodList.size))
        } else if (startIndex < endIndex) {
            val pList = specificOutletsFoodList.subList(startIndex, specificOutletsFoodList.size)
            return FoodsList(pList, pList.toList().size, pageNumber, specificOutletsFoodList.size, true)
        }
        return FoodsList(emptyList(), 0, 0 ,0, false)

    }
}