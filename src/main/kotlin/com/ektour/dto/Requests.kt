package com.ektour.dto

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.PositiveOrZero
import javax.validation.constraints.Size

data class AdminSearchForm(
        @field:NotNull
        @field:DateTimeFormat(pattern = "yyyy-MM-dd")
        var start: LocalDate? = null,

        @field:NotNull
        @field:DateTimeFormat(pattern = "yyyy-MM-dd")
        var end: LocalDate? = null,

        var searchType: String = "",
        var keyword: String = "",
)

data class FindEstimateRequest(

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
) {
        fun passwordCheck(): Boolean = newPassword == newPasswordCheck
}