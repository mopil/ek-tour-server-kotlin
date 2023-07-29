package com.ektour.api.dto

import com.ektour.model.domain.Estimate
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.*

data class CreateUpdateEstimateRequest(
    @field:NotBlank
    val name: String,

    @field:Email(message = "이메일 형식이 아닙니다")
    val email: String,

    @field:NotBlank
    @field:Size(min = 11, max = 11, message = "01012341234 형식이 아닙니다")
    val phone: String,

    @field:NotBlank
    @field:Size(min = 4, max = 4, message = "비밀번호는 4자리로 설정해주세요")
    @field:PositiveOrZero(message = "비밀번호는 0~9자리 숫자로만 가능합니다")
    val password: String = "1234",

    // 필수
    @field:NotEmpty val vehicleType: String = "기본값",
    val vehicleNumber: Int = 0,
    val memberCount: Int = 0,
    @field:NotEmpty val departDate: String = "기본값",
    @field:NotEmpty val arrivalDate: String = "기본값",
    @field:NotEmpty val departPlace: String = "기본값",
    @field:NotEmpty val arrivalPlace: String = "기본값",
    var memo: String,

    // 선택
    @ApiModelProperty(required = false)
    val travelType: String = "",
    @ApiModelProperty(required = false)
    val stopPlace: String = "",
    @ApiModelProperty(required = false)
    val wayType: String = "",
    @ApiModelProperty(required = false)
    val payment: String = "",
    @ApiModelProperty(required = false)
    val taxBill: String = "",
    @ApiModelProperty(required = false)
    val ip: String = ""
) {
    fun toEntity(ip: String) = Estimate(
        name = name,
        email = email,
        phone = phone,
        password = password,
        travelType = travelType,
        vehicleType = vehicleType,
        vehicleNumber = vehicleNumber,
        memberCount = memberCount,
        departDate = departDate,
        arrivalDate = arrivalDate,
        departPlace = departPlace,
        arrivalPlace = arrivalPlace,
        memo = memo,
        stopPlace = stopPlace,
        wayType = wayType,
        payment = payment,
        taxBill = taxBill,
        ip = ip
    )
}