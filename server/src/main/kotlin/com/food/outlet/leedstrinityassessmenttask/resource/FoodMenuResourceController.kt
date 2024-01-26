package com.food.outlet.leedstrinityassessmenttask.resource

import com.food.outlet.leedstrinityassessmenttask.config.ErrorResponse
import com.food.outlet.leedstrinityassessmenttask.data.FoodsDataDTO
import com.food.outlet.leedstrinityassessmenttask.data.FoodsList
import com.food.outlet.leedstrinityassessmenttask.service.FoodsDataService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@RestController
@Validated
@RequestMapping("/foods", produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.IMAGE_JPEG_VALUE])
class FoodMenuResourceController(
  private val foodsDataService: FoodsDataService,
) {


  @Operation(summary = "Get all available Foods.", description = "All foods data available from outlets in the university")
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
  fun getFoodsAllOutlets(
    @Schema(example = "0", required = true)
    @Parameter(required = true, description = "Zero-based page index (0..N)")
    @RequestParam(value = "page", defaultValue = "0")
    page: Int,
    @Schema(example = "10", required = true)
    @Parameter(required = true, description = "The size of the page to be returned")
    @RequestParam(value = "size", defaultValue = "10")
    size: Int,
  ): FoodsList {
    return foodsDataService.getAllFoodsMenuData(page, size)
  }

  @Operation(summary = "Get all available Foods in specific outlets.", description = "All foods data available from outlets in the university")
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
  @GetMapping("/v1/{outletId}")
  fun getFoodsSpecificOutlet(
    @Schema(example = "001", required = true)
    @PathVariable("outletId")
    @Parameter(required = true)
    outletId: Int,
    @Schema(example = "0", required = true)
    @Parameter(required = true, description = "Zero-based page index (0..N)")
    @RequestParam(value = "page", defaultValue = "0")
    page: Int,
    @Schema(example = "10", required = true)
    @Parameter(required = true, description = "The size of the page to be returned, when given 0 shall return all records")
    @RequestParam(value = "size", defaultValue = "0")
    size: Int,
  ): FoodsList {
    return foodsDataService.getOutletFoodsMenuData(outletId, page, size)
  }
}
