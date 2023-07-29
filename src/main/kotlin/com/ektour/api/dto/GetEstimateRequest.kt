package com.ektour.api.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.PositiveOrZero
import javax.validation.constraints.Size

data class GetEstimateRequest(

    @field:NotBlank
    @field:Size(min = 11, max = 11, message = "01012341234 형식이 아닙니다")
    val phone: String,

    @field:NotBlank
    @field:Size(min = 4, max = 4, message = "비밀번호는 4자리로 설정해주세요")
    @field:PositiveOrZero(message = "비밀번호는 0~9자리 숫자로만 가능합니다")
    val password: String,
)
