package com.ektour.service

import com.ektour.api.dto.BoolResponse
import com.ektour.api.dto.CreateUpdateEstimateRequest
import com.ektour.api.dto.GetAllEstimateSimpleByPagingResponse
import com.ektour.api.dto.GetEstimateDetailResponse
import com.ektour.api.dto.GetEstimateRequest
import com.ektour.api.dto.GetEstimateSimpleResponse
import com.ektour.api.util.IpExtractor.getIp
import com.ektour.model.domain.EstimateRepository
import com.ektour.web.dto.AdminSearchForm
import com.ektour.web.dto.EstimateDetailDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.servlet.http.HttpServletRequest

@Service
@Transactional(readOnly = true)
class EstimateService(
    private val estimateRepository: EstimateRepository
) {
    private fun getEstimate(id: Long) = estimateRepository.findById(id).orElseThrow()

    @Transactional
    fun createEstimate(
        request: HttpServletRequest,
        form: CreateUpdateEstimateRequest
    ): GetEstimateDetailResponse {
        val entity = estimateRepository.save(form.toEntity(request.getIp()))
        return GetEstimateDetailResponse(entity)
    }

    fun getEstimateDetailWithForm(id: Long, form: GetEstimateRequest): GetEstimateDetailResponse {
        val estimate = estimateRepository.findByIdAndPhoneAndPassword(id, form.phone, form.password)
            ?: throw IllegalArgumentException("견적서가 존재하지 않습니다.")
        return GetEstimateDetailResponse(estimate)
    }

    fun getEstimateToDto(id: Long): EstimateDetailDto {
        return EstimateDetailDto(getEstimate(id))
    }

    fun getEstimateDetailWithoutForm(id: Long): GetEstimateDetailResponse {
        return GetEstimateDetailResponse(getEstimate(id))
    }

    fun getAllEstimatesToFrontendByPaging(pageable: Pageable): GetAllEstimateSimpleByPagingResponse {
        return GetAllEstimateSimpleByPagingResponse(estimateRepository.findAll(pageable))
    }

    fun getAllEstimatesToAdminByPaging(pageable: Pageable): Page<EstimateDetailDto> {
        return estimateRepository.findAllByAdmin(pageable)
            .map { EstimateDetailDto(it) }
    }

    fun getAllMyEstimatesToFrontendWithPaging(
        pageable: Pageable,
        form: GetEstimateRequest
    ): GetAllEstimateSimpleByPagingResponse {
        val result = estimateRepository.findAllByPhoneAndPassword(pageable, form.phone, form.password)
        return GetAllEstimateSimpleByPagingResponse(result)
    }

    fun searchEstimatesByAdmin(pageable: Pageable, form: AdminSearchForm): Page<EstimateDetailDto> {
        val startDate = form.start.toString()
        val endDate = form.end.plusDays(1L).toString()
        return if (form.keyword == "") {
            estimateRepository
                .searchAllByDate(pageable, startDate, endDate)
                .map { EstimateDetailDto(it) }
        } else if (form.searchType == "phone") {
            estimateRepository
                .searchAllByPhone(pageable, startDate, endDate, form.keyword)
                .map { EstimateDetailDto(it) }
        } else {
            estimateRepository
                .searchAllByName(pageable, startDate, endDate, form.keyword)
                .map { EstimateDetailDto(it) }
        }
    }

    fun getAllMyEstimatesToFrontendWithoutPaging(form: GetEstimateRequest): List<GetEstimateSimpleResponse> =
        estimateRepository
            .findAllByPhoneAndPassword(form.phone, form.password)
            .reversed()
            .map { GetEstimateSimpleResponse(it) }

    @Transactional
    fun updateEstimateByFrontend(
        id: Long,
        form: CreateUpdateEstimateRequest
    ): GetEstimateDetailResponse {
        val estimate = getEstimate(id)
        estimate.updateByFrontend(form)
        return GetEstimateDetailResponse(estimate)
    }

    @Transactional
    fun updateEstimateByAdmin(id: Long, form: EstimateDetailDto): EstimateDetailDto {
        val estimate = getEstimate(id)
        estimate.updateByAdmin(form)
        return EstimateDetailDto(estimate)
    }

    @Transactional
    fun softDelete(id: Long): BoolResponse {
        getEstimate(id).softDelete()
        return BoolResponse(true)
    }

    @Transactional
    fun hardDelete(id: Long): BoolResponse {
        estimateRepository.deleteById(id)
        return BoolResponse(true)
    }
}
