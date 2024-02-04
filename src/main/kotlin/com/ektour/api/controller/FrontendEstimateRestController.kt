package com.ektour.api.controller

import com.ektour.api.Uris
import com.ektour.api.dto.BoolResponse
import com.ektour.api.dto.CreateUpdateEstimateRequest
import com.ektour.api.dto.GetAllEstimateSimpleByPagingResponse
import com.ektour.api.dto.GetEstimateDetailResponse
import com.ektour.api.dto.GetEstimateRequest
import com.ektour.api.dto.GetEstimateSimpleResponse
import com.ektour.service.EmailService
import com.ektour.service.EstimateService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@Api(tags = ["프론트엔드 - 견적요청 REST API"])
class FrontendEstimateRestController(
    private val estimateService: EstimateService,
    private val emailService: EmailService
) {
    @ApiOperation("견적요청 생성(저장)")
    @PostMapping(Uris.EstimateApis.CREATE_ESTIMATE)
    fun createEstimate(
        @Valid @RequestBody form: CreateUpdateEstimateRequest,
        request: HttpServletRequest
    ): GetEstimateDetailResponse {
        val result = estimateService.createEstimate(request, form)
        emailService.sendMail(form)
        return result
    }

    @ApiOperation("핸드폰 번호, 비밀번호와 함께 견적요청 조회")
    @PostMapping(Uris.EstimateApis.GET_ESTIMATE_WITH_PHONE_AND_PASSWORD)
    fun getEstimateDetailWithForm(
        @PathVariable id: Long,
        @Valid @RequestBody form: GetEstimateRequest
    ): GetEstimateDetailResponse {
        return estimateService.getEstimateDetailWithForm(id, form)
    }

    @ApiOperation("폼 없이 견적요청 조회")
    @GetMapping(Uris.EstimateApis.GET_ESTIMATE_WITHOUT_FORM)
    fun getEstimateDetailWithoutForm(
        @PathVariable id: Long
    ): GetEstimateDetailResponse {
        return estimateService.getEstimateDetailWithoutForm(id)
    }

    @ApiOperation("견적요청 목록 조회(페이징)")
    @GetMapping(Uris.EstimateApis.GET_ALL_ESTIMATES_BY_PAGING)
    fun getAllEstimatesToFrontendByPaging(
        pageable: Pageable
    ): GetAllEstimateSimpleByPagingResponse {
        return estimateService.getAllEstimatesToFrontendByPaging(pageable)
    }

    @ApiOperation("내가 쓴 견적요청 목록 조회 (페이징X)")
    @PostMapping(Uris.EstimateApis.GET_ALL_MY_ESTIMATES_WITHOUT_PAGING)
    fun getAllMyEstimatesToFrontendWithNoPaging(
        @Valid @RequestBody form: GetEstimateRequest
    ): List<GetEstimateSimpleResponse> {
        return estimateService.getAllMyEstimatesToFrontendWithoutPaging(form)
    }

    @ApiOperation("클라이언트 내가 쓴 견적요청 목록 조회 (페이징)")
    @PostMapping(Uris.EstimateApis.GET_ALL_MY_ESTIMATES_WITH_PAGING)
    fun getAllMyEstimatesToFrontendWithPaging(
        @Valid @RequestBody form: GetEstimateRequest,
        pageable: Pageable,
    ): GetAllEstimateSimpleByPagingResponse {
        return estimateService.getAllMyEstimatesToFrontendWithPaging(pageable, form)
    }

    @ApiOperation("견적요청 수정")
    @PutMapping(Uris.EstimateApis.UPDATE_MY_ESTIMATE)
    fun updateEstimateByFrontend(
        @PathVariable id: Long,
        @Valid @RequestBody form: CreateUpdateEstimateRequest
    ): GetEstimateDetailResponse {
        return estimateService.updateEstimateByFrontend(id, form)
    }

    @ApiOperation("견적요청 삭제 (소프트)")
    @DeleteMapping(Uris.EstimateApis.SOFT_DELETE_MY_ESTIMATE)
    fun softDelete(@PathVariable id: Long): BoolResponse {
        return estimateService.softDelete(id)
    }
}
