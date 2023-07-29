package com.ektour.web.controller

import com.ektour.common.auth.AdminAuthenticate
import com.ektour.service.EstimateService
import com.ektour.service.ExcelService
import com.ektour.service.VisitLogService
import com.ektour.utils.AdminSearchFormStorage
import com.ektour.web.dto.AdminSearchForm
import com.ektour.web.dto.EstimateDetailDto
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/admin")
@Api(tags = ["관리자페이지 - 견적요청 API"])
@AdminAuthenticate
class AdminEstimateController(
    private val estimateService: EstimateService,
    private val adminSearchFormStorage: AdminSearchFormStorage,
    private val excelService: ExcelService,
    private val visitLogService: VisitLogService
) {
    private fun setPagingModels(
        model: Model,
        pageable: Pageable,
        eList: Page<EstimateDetailDto>,
        form: AdminSearchForm
    ) {
        model["currentPage"] = pageable.pageNumber
        model["eList"] = eList
        model["maxPage"] = 10
        model["adminSearchForm"] = form
        model["visitToday"] = visitLogService.getTodayVisitCount()
        model["visitTotal"] = visitLogService.getTotalVisitCount()
    }

    @ApiOperation("관리자페이지 메인화면 조회")
    @GetMapping("/main")
    fun main(model: Model, pageable: Pageable): String {
        setPagingModels(
            model = model,
            pageable = pageable,
            eList = estimateService.getAllEstimatesToAdminByPaging(pageable),
            form = AdminSearchForm()
        )
        return "mainPage"
    }

    private fun setSearchParams(form: AdminSearchForm, pageable: Pageable, model: Model) {
        adminSearchFormStorage.set(form)
        setPagingModels(
            model = model,
            pageable = pageable,
            eList = estimateService.searchEstimatesByAdmin(pageable, form),
            form = form
        )
    }

    @ApiOperation("검색 요청 최초 검색")
    @PostMapping("/search")
    fun search(
        @Valid @ModelAttribute("adminSearchForm") form: AdminSearchForm,
        pageable: Pageable,
        model: Model
    ): String {
        setSearchParams(form, pageable, model)
        return "searchPage"
    }

    @ApiOperation("검색한 상태에서 페이징")
    @GetMapping("/search")
    fun searchPaging(
        pageable: Pageable,
        model: Model
    ): String {
        setSearchParams(form = adminSearchFormStorage.get(), pageable, model)
        return "searchPage"
    }

    @ApiOperation("견적요청 상세 조회")
    @GetMapping("/estimate/{id}")
    fun getEstimateDetail(@PathVariable("id") id: Long, model: Model): String {
        model["estimate"] = estimateService.getEstimateToDto(id)
        model["visitToday"] = visitLogService.getTodayVisitCount()
        model["visitTotal"] = visitLogService.getTotalVisitCount()
        return "estimateDetailPage"
    }

    @ApiOperation("견적요청 엑셀 다운로드")
    @GetMapping("/estimate/{id}/excel")
    fun downloadExcel(
        @PathVariable("id") id: Long,
        response: HttpServletResponse
    ) = excelService.createExcel(id, response)

    @ApiOperation("견적요청 수정")
    @PostMapping("/update/estimate/{id}")
    fun updateEstimate(
        @PathVariable("id") id: Long,
        @ModelAttribute("estimate") form: EstimateDetailDto
    ): String {
        estimateService.updateEstimateByAdmin(id, form)
        return "redirect:/admin/main"
    }

    @ApiOperation("견적요청 삭제(하드)")
    @PostMapping("/delete/estimate/{id}")
    fun deleteEstimateHardly(@PathVariable("id") id: Long): String {
        estimateService.hardDelete(id)
        return "redirect:/admin/main"
    }
}
