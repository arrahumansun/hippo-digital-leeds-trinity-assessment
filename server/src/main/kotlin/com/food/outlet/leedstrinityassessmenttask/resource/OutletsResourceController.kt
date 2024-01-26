package com.food.outlet.leedstrinityassessmenttask.resource

import com.food.outlet.leedstrinityassessmenttask.config.ErrorResponse
import com.food.outlet.leedstrinityassessmenttask.data.OutletsDataDTO
import com.food.outlet.leedstrinityassessmenttask.data.OutletsList
import com.food.outlet.leedstrinityassessmenttask.service.OutletsDataService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@Validated
@RequestMapping("/outlets", produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.IMAGE_JPEG_VALUE])
class OutletsResourceController(
  private val outletsDataService: OutletsDataService,
) {

  @Operation(summary = "Get all available outlets.", description = "All outlets data available in the university")
  @ApiResponses(
    value = [
      ApiResponse(
        responseCode = "200",
        description = "Successful Operation",
      ),
      ApiResponse(
        responseCode = "401",
        description = "Unauthorized to access this endpoint",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class))],
      ),
      ApiResponse(
        responseCode = "403",
        description = "Forbidden, requires an appropriate role",
        content = [
          Content(
            mediaType = "application/json",
            schema = Schema(implementation = ErrorResponse::class),
          ),
        ],
      ),
      ApiResponse(
        responseCode = "400",
        description = "Incorrect input options provided",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class))],
      ),
    ],
  )
  @GetMapping("/v1")
  fun getAllOutlets(
    @Schema(example = "0", required = true)
    @Parameter(required = true, description = "Zero-based page index (0..N)")
    @RequestParam(value = "page", defaultValue = "0")
    page: Int,
    @Schema(example = "10", required = true)
    @Parameter(required = true, description = "The size of the page to be returned")
    @RequestParam(value = "size", defaultValue = "10")
    size: Int,
  ): OutletsList {
    return outletsDataService.getAllOutletsData(page, size)
  }
}
