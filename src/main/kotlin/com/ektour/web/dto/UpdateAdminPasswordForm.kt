package com.ektour.web.dto

import javax.validation.constraints.NotBlank

data class UpdateAdminPasswordForm(
    @field:NotBlank val nowPassword: String = "",
    @field:NotBlank val newPassword: String = "",
    @field:NotBlank val newPasswordCheck: String = "",
) {
    fun isSamePassword(): Boolean = newPassword == newPasswordCheck
}
