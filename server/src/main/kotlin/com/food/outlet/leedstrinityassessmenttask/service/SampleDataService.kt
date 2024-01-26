package com.food.outlet.leedstrinityassessmenttask.service

import com.food.outlet.leedstrinityassessmenttask.data.SampleDataDTO
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SampleDataService() {

    fun getSampleData(sampleId: String): SampleDataDTO {
        val sampleDataDTO: SampleDataDTO = SampleDataDTO(sampleId, LocalDateTime.now(), "Data Generated as " + sampleId + " requested @ " + LocalDateTime.now())
        return sampleDataDTO
    }
}