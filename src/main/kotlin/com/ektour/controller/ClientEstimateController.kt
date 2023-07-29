package com.ektour.controller

import com.ektour.dto.*
import com.ektour.dto.BoolResponse
import com.ektour.service.EmailService
import com.ektour.service.EstimateService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/estimate")
@Api(tags = ["클라이언트 - 견적요청 REST API"])
class ClientEstimateController(
    private val estimateService: EstimateService,
    private val emailService: EmailService
) {
    @ApiOperation("견적요청 생성(저장)")
    @PostMapping
    fun saveAndAlarm(
        @Valid @RequestBody form: EstimateForm,
        request: HttpServletRequest
    ): EstimateDetailDto {
        val result = estimateService.createEstimate(request, form)
        emailService.sendMail(form)
        return result
    }

    @ApiOperation("핸드폰 번호, 비밀번호와 함께 견적요청 조회")
    @PostMapping("/{id}")
    fun findByPhoneAndPassword(
        @PathVariable("id") id: Long,
        @Valid @RequestBody form: FindEstimateForm
    ): EstimateDetailDto = estimateService.getEstimate(id, form)

    @ApiOperation("폼 없이 견적요청 조회")
    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: Long): EstimateDetailDto =
        estimateService.getEstimateToDto(id)

    @ApiOperation("클라이언트 견적요청 목록 조회(페이징)")
    @GetMapping("/all")
    fun findAllByPageClient(
        @PageableDefault(
            sort = ["id"],
            direction = Sort.Direction.DESC
        ) pageable: Pageable,
    ): EstimatePagedResponse = estimateService.findAllByPage(pageable)

    @ApiOperation("존재하는 전체 페이지 수 조회")
    @GetMapping("/all/page")
    fun getAllPageCount(): PageTotalCountResponse = estimateService.getAllPageCount()

    @ApiOperation("클라이언트 내가 쓴 견적요청 목록 조회 (페이징X)")
    @PostMapping("/search/my")
    fun findAllMyEstimates(
        @Valid @RequestBody form: FindEstimateForm
    ): List<EstimateSimpleResponse> = estimateService.findAllMyEstimates(form)

    @ApiOperation("클라이언트 내가 쓴 견적요청 목록 조회 (페이징)")
    @PostMapping("/search/my/all")
    fun findAllMyEstimatesPaging(
        @Valid @RequestBody form: FindEstimateForm,
        @PageableDefault(
            sort = ["id"],
            direction = Sort.Direction.DESC
        ) pageable: Pageable,
    ): EstimatePagedResponse = estimateService.findAllMyEstimates(pageable, form)

    @ApiOperation("견적요청 수정")
    @PutMapping("/{id}")
    fun updateEstimate(
        @PathVariable("id") id: Long,
        @Valid @RequestBody form: EstimateForm
    ): EstimateDetailDto = estimateService.updateEstimate(id, form)

    @ApiOperation("견적요청 삭제 (소프트)")
    @DeleteMapping("/{id}")
    fun softDelete(@PathVariable("id") id: Long): BoolResponse =
        estimateService.softDelete(id)

    @ApiOperation("견적요청 네이버 메일 알림 전송")
    @PostMapping("/alarm")
    fun alarm(@RequestBody form: EstimateForm): BoolResponse =
        emailService.sendMail(form)
}
