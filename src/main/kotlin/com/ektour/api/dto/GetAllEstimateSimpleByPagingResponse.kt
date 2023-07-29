package com.ektour.api.dto

import com.ektour.model.domain.Estimate
import org.springframework.data.domain.Page

data class GetAllEstimateSimpleByPagingResponse(
    val currentPage: Int,
    val totalPage: Int,
    val currentPageCount: Int,
    val totalCount: Int,
    val estimateList: List<GetEstimateSimpleResponse>,
) {
    constructor(page: Page<Estimate>) : this(
        currentPage = page.pageable.pageNumber,
        totalPage = page.totalPages,
        currentPageCount = page.pageable.pageSize,
        totalCount = page.totalElements.toInt(),
        estimateList = page
            .filter { it.visibility }
            .map { GetEstimateSimpleResponse(it) }
            .toList()
    )
}