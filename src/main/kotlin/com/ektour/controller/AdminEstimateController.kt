package com.ektour.controller

import com.ektour.common.auth.Auth
import com.ektour.dto.AdminSearchForm
import com.ektour.dto.EstimateDetailDto
import com.ektour.service.EstimateService
import com.ektour.service.ExcelService
import com.ektour.service.VisitService
import com.ektour.utils.SearchStorage
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid


@Controller
@RequestMapping("/admin")
class AdminEstimateController(
    private val estimateService: EstimateService,
    private val searchStorage: SearchStorage,
    private val excelService: ExcelService,
    private val visitService: VisitService
) {
    /**
     * 관리자페이지 - 견적요청 조회
     */
    // 페이징 처리 변수 세팅 편의 메소드
    private fun setPagingModels(
        model: Model,
        pageable: Pageable,
        eList: Page<EstimateDetailDto>,
        form: AdminSearchForm)
    {
        model["currentPage"] = pageable.pageNumber
        model["eList"] = eList
        model["maxPage"] = 10
        model["adminSearchForm"] = form
        model["visitToday"] = visitService.getToday()
        model["visitTotal"] = visitService.getTotal()
    }

    @Auth @GetMapping("/main")
    fun main(
        model: Model,
        @PageableDefault(sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable
    ): String {
        setPagingModels(
            model = model,
            pageable = pageable,
            eList = estimateService.findAllByPageAdmin(pageable),
            form = AdminSearchForm()
        )
        return "mainPage"
    }

    /**
     * 견적요청 검색
     */
    // 검색 변수 설정 편의 메소드
    private fun setSearchVars(form: AdminSearchForm, pageable: Pageable, model: Model) {
        searchStorage.setValue("search", form)
        setPagingModels(
            model = model,
            pageable = pageable,
            eList = estimateService.searchByPageAdmin(pageable, form),
            form = form
        )
    }

    // 검색 요청 처음 검색
    @PostMapping("/search")
    fun search(
        @Valid @ModelAttribute("adminSearchForm") form: AdminSearchForm,
        @PageableDefault(sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable,
        model: Model
    ): String {
        setSearchVars(form, pageable, model)
        return "searchPage"
    }

    // 검색한 상태에서 페이징
    @GetMapping("/search")
    fun searchPaging(
        @PageableDefault(sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable,
        model: Model
    ): String {
        setSearchVars(form = searchStorage.getValue("search"), pageable, model)
        return "searchPage"
    }

    // 견적요청 상세 조회
    @GetMapping("/estimate/{id}")
    fun getEstimateDetail(@PathVariable("id") id: Long, model: Model): String {
        model["estimate"] = estimateService.getEstimateToDto(id)
        return "estimateDetailPage"
    }

    /**
     * 견적요청 엑셀 다운로드
     */
    @GetMapping("/estimate/{id}/excel")
    fun downloadExcel(
        @PathVariable("id") id: Long,
        response: HttpServletResponse
    ) = excelService.createExcel(id, response)

    /**
     * 관리자페이지 - 견적요청 수정
     */
    @PostMapping("/update/estimate/{id}")
    fun updateEstimate(
        @PathVariable("id") id: Long,
        @ModelAttribute("estimate") form: EstimateDetailDto
    ): String {
        estimateService.updateEstimateAdmin(id, form)
        return "redirect:/admin/main"
    }

    /**
     * 관리자페이지 - 견적요청 하드 삭제
     */
    @PostMapping("/delete/estimate/{id}")
    fun deleteEstimateHardly(@PathVariable("id") id: Long): String {
        estimateService.hardDelete(id)
        return "redirect:/admin/main"
    }

}