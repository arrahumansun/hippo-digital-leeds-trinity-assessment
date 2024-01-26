package com.food.outlet.leedstrinityassessmenttask.resource

import com.food.outlet.leedstrinityassessmenttask.data.SampleDataDTO
import com.food.outlet.leedstrinityassessmenttask.service.SampleDataService
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@Validated
@RequestMapping("/sample", produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.IMAGE_JPEG_VALUE])
class SampleResourceController(
  private val sampleDataService: SampleDataService,
) {

  @GetMapping("/getData/{sampleId}")
  fun getSampleData(
    @PathVariable sampleId: String,
  ): SampleDataDTO {
    return sampleDataService.getSampleData(sampleId)
  }
}
