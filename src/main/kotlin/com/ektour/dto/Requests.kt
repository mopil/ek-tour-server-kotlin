package com.ektour.dto

import com.ektour.entity.Estimate
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import javax.validation.constraints.*

data class AdminSearchForm(
    @field:NotNull
    @field:DateTimeFormat(pattern = "yyyy-MM-dd")
    var start: LocalDate = LocalDate.now(),

    @field:NotNull
    @field:DateTimeFormat(pattern = "yyyy-MM-dd")
    var end: LocalDate = LocalDate.now(),

    var searchType: String = "",
    var keyword: String = "",
)

data class FindEstimateForm(

    @field:NotBlank
    @field:Size(min = 11, max = 11, message = "01012341234 형식이 아닙니다")
    var phone: String = "",

    @field:NotBlank
    @field:Size(min = 4, max = 4, message = "비밀번호는 4자리로 설정해주세요")
    @field:PositiveOrZero(message = "비밀번호는 0~9자리 숫자로만 가능합니다")
    var password: String = "",
)

data class UpdateAdminPasswordForm(
    @field:NotBlank var nowPassword: String = "",
    @field:NotBlank var newPassword: String = "",
    @field:NotBlank var newPasswordCheck: String = "",
) { fun isSamePassword(): Boolean = newPassword == newPasswordCheck }

data class EstimateForm(
    // 신청자 정보
    @field:NotBlank
    var name: String,

    @field:Email(message = "이메일 형식이 아닙니다")
    var email: String,

    @field:NotBlank
    @field:Size(min = 11, max = 11, message = "01012341234 형식이 아닙니다")
    var phone: String,

    @field:NotBlank
    @field:Size(min = 4, max = 4, message = "비밀번호는 4자리로 설정해주세요")
    @field:PositiveOrZero(message = "비밀번호는 0~9자리 숫자로만 가능합니다")
    var password: String,

    // 필수
    @field:NotEmpty var vehicleType: String,
    var vehicleNumber: Int = 0,
    var memberCount: Int = 0,
    @field:NotEmpty var departDate: String,
    @field:NotEmpty var arrivalDate: String,
    @field:NotEmpty var departPlace: String,
    @field:NotEmpty var arrivalPlace: String,
    @field:NotEmpty var memo: String,

    // 선택
    var travelType: String,
    var stopPlace: String,
    var wayType: String,
    var payment: String,
    var taxBill: String,
    var ip: String
)

fun EstimateForm.toEntity() = Estimate(
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