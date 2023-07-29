package com.ektour.web.controller

import com.ektour.common.auth.AdminAuthenticate
import com.ektour.service.EstimateService
import com.ektour.service.ExcelService
import com.ektour.utils.AdminSearchFormStorage
import com.ektour.web.StaticPages
import com.ektour.web.ViewModelUtils
import com.ektour.web.WebUris
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
@Api(tags = ["관리자페이지 - 견적요청"])
@AdminAuthenticate
class AdminEstimateController(
    private val estimateService: EstimateService,
    private val adminSearchFormStorage: AdminSearchFormStorage,
    private val excelService: ExcelService,
    private val viewModelUtils: ViewModelUtils
) {
    companion object {
        const val ESTIMATE = "estimate"
        const val ADMIN_SEARCH_FORM = "adminSearchForm"
        const val CURRENT_PAGE = "currentPage"
        const val MAX_PAGE = "maxPage"
        const val ESTIMATE_LIST = "eList"
    }
    private fun setPagingModels(
        model: Model,
        pageable: Pageable,
        eList: Page<EstimateDetailDto>,
        form: AdminSearchForm
    ) {
        model[CURRENT_PAGE] = pageable.pageNumber
        model[ESTIMATE_LIST] = eList
        model[MAX_PAGE] = 10
        model[ADMIN_SEARCH_FORM] = form
        viewModelUtils.addVisitCount(model)
    }

    @ApiOperation("관리자페이지 메인화면 조회")
    @GetMapping(WebUris.Admin.GO_ADMIN_MAIN_PAGE)
    fun main(model: Model, pageable: Pageable): String {
        setPagingModels(
            model = model,
            pageable = pageable,
            eList = estimateService.getAllEstimatesToAdminByPaging(pageable),
            form = AdminSearchForm()
        )
        return StaticPages.ADMIN_MAIN
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
    @PostMapping(WebUris.Admin.ESTIMATE_FIRST_SEARCH)
    fun search(
        @Valid @ModelAttribute(ADMIN_SEARCH_FORM) form: AdminSearchForm,
        pageable: Pageable,
        model: Model
    ): String {
        setSearchParams(form, pageable, model)
        return StaticPages.ESTIMATE_SEARCH
    }

    @ApiOperation("검색한 상태에서 페이징")
    @GetMapping(WebUris.Admin.SEARCHING_WITH_PAGING)
    fun searchPaging(
        pageable: Pageable,
        model: Model
    ): String {
        setSearchParams(form = adminSearchFormStorage.get(), pageable, model)
        return StaticPages.ESTIMATE_SEARCH
    }

    @ApiOperation("견적요청 상세 조회")
    @GetMapping(WebUris.Admin.GO_ESTIMATE_DETAIL_PAGE)
    fun getEstimateDetail(@PathVariable id: Long, model: Model): String {
        model[ESTIMATE] = estimateService.getEstimateToDto(id)
        viewModelUtils.addVisitCount(model)
        return StaticPages.ESTIMATE_DETAIL
    }

    @ApiOperation("견적요청 엑셀 다운로드")
    @GetMapping(WebUris.Admin.DOWNLOAD_EXCEL)
    fun downloadExcel(
        @PathVariable id: Long,
        response: HttpServletResponse
    ) = excelService.createExcel(id, response)

    @ApiOperation("견적요청 수정")
    @PostMapping(WebUris.Admin.UPDATE_ESTIMATE)
    fun updateEstimate(
        @PathVariable id: Long,
        @ModelAttribute(ESTIMATE) form: EstimateDetailDto
    ): String {
        estimateService.updateEstimateByAdmin(id, form)
        return StaticPages.Redirect.ADMIN_MAIN
    }

    @ApiOperation("견적요청 삭제(하드)")
    @PostMapping(WebUris.Admin.HARD_DELETE_ESTIMATE)
    fun deleteEstimateHardly(@PathVariable id: Long): String {
        estimateService.hardDelete(id)
        return StaticPages.Redirect.ADMIN_MAIN
    }
}
