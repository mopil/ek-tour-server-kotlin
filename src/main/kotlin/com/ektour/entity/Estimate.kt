package com.ektour.entity

import com.ektour.dto.EstimateDetailDto
import com.ektour.dto.EstimateForm
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
class Estimate(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    // 신청자 정보
    var name: String,
    var email: String,
    var phone: String,
    var password: String,

    // 필수
    var travelType: String, // 일반여행, 관혼상제, 학교단체, 기타단체
    var vehicleType: String, // 25인승 소형, 28인승 리무진, 45인승 대형
    var vehicleNumber: Int, // 차량대수
    var memberCount: Int, // 인원수
    var departDate: String, // 출발일시
    var arrivalDate: String, // 도착일시
    var departPlace: String, // 출발지
    var arrivalPlace: String, // 도착지
    var memo: String, // 기타메모

    // 선택
    var stopPlace: String, // 경유지
    var wayType: String, // 왕복구분 : 왕복, 편도
    var payment: String, // 결제방법 : 현금, 카드
    var taxBill: String, // 세금계산서 : 발급, 발급안함

    // 견적 요청 삭제여부 (사용자에게 보여지는 여부)
    var visibility: Boolean = true,

    var createdDate: String = "", // 견적 요청일
    var validDate: String = "", // 견적 요청일로 부터 +7일

    var ip: String,

) {
    @PrePersist
    fun onPrePersist() {
        createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        validDate = LocalDateTime.now().plusDays(7).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    }

    fun softDelete() { this.visibility = false }

    fun update(form: EstimateForm): Estimate {
        this.name = form.name
        this.email = form.email
        this.phone = form.phone
        this.password = form.password
        this.travelType = form.travelType
        this.vehicleType = form.vehicleType
        this.vehicleNumber = form.vehicleNumber
        this.memberCount = form.memberCount
        this.departDate = form.departDate
        this.arrivalDate = form.arrivalDate
        this.departPlace = form.departPlace
        this.arrivalPlace = form.arrivalPlace
        this.memo = form.memo
        this.stopPlace = form.stopPlace
        this.wayType = form.wayType
        this.payment = form.payment
        this.taxBill = form.taxBill
        return this
    }

    fun update(form: EstimateDetailDto): Estimate {
        this.name = form.name
        this.email = form.email
        this.phone = form.phone
        this.password = form.password
        this.travelType = form.travelType
        this.vehicleType = form.vehicleType
        this.vehicleNumber = form.vehicleNumber
        this.memberCount = form.memberCount
        this.departDate = form.departDate
        this.arrivalDate = form.arrivalDate
        this.departPlace = form.departPlace
        this.arrivalPlace = form.arrivalPlace
        this.memo = form.memo
        this.stopPlace = form.stopPlace
        this.wayType = form.wayType
        this.payment = form.payment
        this.taxBill = form.taxBill
        this.visibility = form.visibility
        return this
    }
}
