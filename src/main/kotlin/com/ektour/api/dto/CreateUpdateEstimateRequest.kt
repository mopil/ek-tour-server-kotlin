package com.ektour.api.dto

import com.ektour.model.PaymentMethods
import com.ektour.model.TaxBillYesOrNo
import com.ektour.model.TravelType
import com.ektour.model.VehicleType
import com.ektour.model.WayType
import com.ektour.model.domain.Estimate
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.PositiveOrZero
import javax.validation.constraints.Size

data class CreateUpdateEstimateRequest(
    @ApiModelProperty(value = "요청자 이름", required = true)
    @field:NotBlank
    val name: String,

    @ApiModelProperty(value = "요청자 이메일", required = true)
    @field:Email(message = "이메일 형식이 아닙니다")
    val email: String,

    @ApiModelProperty(value = "요청자 전화번호", required = true)
    @field:NotBlank
    @field:Size(min = 11, max = 11, message = "01012341234 형식이 아닙니다")
    val phone: String,

    @ApiModelProperty(value = "해당 견적 비밀번호", required = true)
    @field:NotBlank
    @field:Size(min = 4, max = 4, message = "비밀번호는 4자리로 설정해주세요")
    @field:PositiveOrZero(message = "비밀번호는 0~9자리 숫자로만 가능합니다")
    val password: String,

    @ApiModelProperty(value = "차량구분 (25인승 소형, 28인승 리무진, 45인승 대형)", required = true)
    @field:NotEmpty
    val vehicleType: String,

    @ApiModelProperty(value = "차량수", required = true)
    val vehicleNumber: Int = 0,

    @ApiModelProperty(value = "인원수", required = true)
    val memberCount: Int = 0,

    @ApiModelProperty(value = "출발일시", required = true)
    @field:NotEmpty
    val departDate: String,

    @field:NotEmpty
    @ApiModelProperty(value = "도착일시", required = true)
    val arrivalDate: String,

    @ApiModelProperty(value = "출발장소", required = true)
    @field:NotEmpty
    val departPlace: String,

    @ApiModelProperty(value = "도착장소", required = true)
    @field:NotEmpty
    val arrivalPlace: String,

    @ApiModelProperty(value = "메모", required = false)
    val memo: String,

    @ApiModelProperty(value = "여행구분(일반여행, 관혼상제, 학교단체, 기타단체)", required = true)
    val travelType: String,

    @ApiModelProperty(value = "경유지", required = false)
    val stopPlace: String?,

    @ApiModelProperty(value = "왕복구분 (왕복, 편도)", required = false)
    val wayType: String?,

    @ApiModelProperty(value = "계산방법 (카드, 현금)", required = false)
    val payment: String?,

    @ApiModelProperty(value = "세금계산서 발급여부 (발급, 발급안함)", required = false)
    val taxBill: String?,

    @ApiModelProperty(value = "IP", required = false)
    val ip: String? = ""
) {
    fun toEntity(ip: String) = Estimate(
        name = name,
        email = email,
        phone = phone,
        password = password,
        travelType = TravelType.from(travelType),
        vehicleType = VehicleType.fromKor(vehicleType),
        vehicleNumber = vehicleNumber,
        memberCount = memberCount,
        departDate = departDate,
        arrivalDate = arrivalDate,
        departPlace = departPlace,
        arrivalPlace = arrivalPlace,
        memo = memo,
        stopPlace = stopPlace ?: "",
        wayType = WayType.fromKor(wayType),
        payment = PaymentMethods.fromKor(payment),
        taxBillYesOrNo = TaxBillYesOrNo.fromKor(taxBill),
        ip = ip
    )
}
