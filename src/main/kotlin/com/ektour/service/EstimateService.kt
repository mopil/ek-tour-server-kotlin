package com.ektour.service

import com.ektour.dto.BoolResponse
import com.ektour.dto.*
import com.ektour.entity.Estimate
import com.ektour.repository.EstimateRepository
import com.ektour.utils.getIp
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.servlet.http.HttpServletRequest

@Service
@Transactional(readOnly = true)
class EstimateService(private val estimateRepository: EstimateRepository) {

    private fun Estimate.toDetailResponse() = EstimateDetailDto(
        id = id, name = name, email = email, phone = phone, password = password,
        travelType = travelType, vehicleType = vehicleType, vehicleNumber = vehicleNumber,
        memberCount = memberCount, departDate = departDate, arrivalDate = arrivalDate,
        departPlace = departPlace, arrivalPlace = arrivalPlace, memo = memo,
        stopPlace = stopPlace, wayType = wayType, payment = payment, taxBill = taxBill,
        visibility = visibility, createdDate = createdDate, ip = ip
    )

    private fun Estimate.toSimpleResponse() = EstimateSimpleResponse(
        id = id, name = name, travelType = travelType, vehicleType = vehicleType,
        departPlace = departPlace, arrivalPlace = arrivalPlace, createdDate = createdDate
    )

    private fun Page<Estimate>.toListResponse() = EstimatePagedResponse(
        currentPage = this.pageable.pageNumber,
        totalPage = this.totalPages,
        currentPageCount = this.pageable.pageSize,
        totalCount = estimateRepository.countAll(),
        estimateList = this
            .filter { it.visibility }
            .map { it.toSimpleResponse() }
            .toList()
    )

    private fun getEstimate(id: Long): Estimate =
        estimateRepository.findById(id).orElseThrow()

    /**
     * 견적요청 생성(저장)
     */
    @Transactional
    fun createEstimate(request: HttpServletRequest, form: EstimateForm): EstimateDetailDto {
        form.ip = request.getIp()
        return estimateRepository.save(form.toEntity()).toDetailResponse()
    }

    /**
     * 견적 요청 상세 조회
     */

    // 하나 조회 (상세 페이지용)
    fun getEstimate(id: Long, form: FindEstimateForm): EstimateDetailDto =
        estimateRepository
            .findByIdAndPhoneAndPassword(id, form.phone, form.password)
            ?.toDetailResponse()
            ?: throw NoSuchElementException("해당 데이터가 없습니다.")

    fun getEstimateToDto(id: Long): EstimateDetailDto = getEstimate(id).toDetailResponse()

    /**
     * 견적 요청 목록 조회
     */
    // 클라이언트로 내려지는 견적요청 목록 (페이징)
    fun findAllByPage(pageable: Pageable): EstimatePagedResponse =
        estimateRepository.findAll(pageable).toListResponse()

    // 관리자페이지 내려지는 견적요청 목록 (페이징)
    fun findAllByPageAdmin(pageable: Pageable): Page<EstimateDetailDto> =
        estimateRepository.findAllByAdmin(pageable).map { it.toDetailResponse() }

    // 존재하는 모든 페이지 수 조회
    fun getAllPageCount() = PageTotalCountResponse(
        (estimateRepository.countAll() / 15) + 1
    )

    // 클라이언트 내가 쓴 견적 조회
    fun findAllMyEstimates(
        pageable: Pageable,
        form: FindEstimateForm
    ): EstimatePagedResponse = estimateRepository
        .findAllByPhoneAndPassword(pageable, form.phone, form.password)
        .toListResponse()

    // 관리자페이지 검색
    fun searchByPageAdmin(pageable: Pageable, form: AdminSearchForm): Page<EstimateDetailDto> {
        val startDate = form.start.toString()
        val endDate = form.end.plusDays(1L).toString()
        return if (form.keyword == "") {
            estimateRepository
                .searchAllByDate(pageable, startDate, endDate)
                .map { it.toDetailResponse() }
        } else if (form.searchType == "phone") {
            estimateRepository
                .searchAllByPhone(pageable, startDate, endDate, form.keyword)
                .map { it.toDetailResponse() }
        } else {
            estimateRepository
                .searchAllByName(pageable, startDate, endDate, form.keyword)
                .map { it.toDetailResponse() }
        }
    }

    // 클라이언트 내가 쓴 견적 조회 페이징 없이 전체 반환
    fun findAllMyEstimates(form: FindEstimateForm): List<EstimateSimpleResponse> =
        estimateRepository
            .findAllByPhoneAndPassword(form.phone, form.password)
            .reversed()
            .map { it.toSimpleResponse() }

    /**
     * 견적요청 수정
     */
    // 클라이언트에서 수정
    @Transactional
    fun updateEstimate(id: Long, form: EstimateForm): EstimateDetailDto =
        getEstimate(id).update(form).toDetailResponse()

    // 관리자 페이지에서 수정
    @Transactional
    fun updateEstimateAdmin(id: Long, form: EstimateDetailDto): EstimateDetailDto =
        getEstimate(id).update(form).toDetailResponse()

    /**
     * 견적요청 삭제
     */
    // 소프트 삭제
    @Transactional
    fun softDelete(id: Long): BoolResponse {
        getEstimate(id).softDelete()
        return BoolResponse(true)
    }

    // 하드 삭제
    @Transactional
    fun hardDelete(id: Long): BoolResponse {
        estimateRepository.deleteById(id)
        return BoolResponse(true)
    }
}
