package com.ektour.controller

import com.ektour.dto.EstimateDetailDto
import com.ektour.dto.EstimateForm
import com.ektour.dto.FindEstimateForm
import com.ektour.service.EmailService
import com.ektour.service.EstimateService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/estimate")
class ClientEstimateController(
    private val estimateService: EstimateService,
    private val emailService: EmailService
) {

    /**
     * 견적요청 생성(저장)
     */
    @PostMapping
    fun saveAndAlarm(@Valid form: EstimateForm, request: HttpServletRequest): EstimateDetailDto =
        estimateService.createEstimate(request, form)

    /**
     * 견적요청 조회
     */
    // 핸드폰 번호, 비밀번호와 함께 조회
    @PostMapping("/{id}")
    fun findByPhoneAndPassword(
        @PathVariable("id") id: Long,
        @Valid @RequestBody form: FindEstimateForm
        ): EstimateDetailDto = estimateService.getEstimate(id, form)

    // 폼 없이 상세 조회
    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: Long): EstimateDetailDto =
        estimateService.getEstimateToDto(id)




}