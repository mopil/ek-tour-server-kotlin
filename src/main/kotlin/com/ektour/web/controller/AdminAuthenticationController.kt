package com.ektour.web.controller

import com.ektour.service.AdminService
import com.ektour.web.StaticPages
import com.ektour.web.WebUris
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import javax.servlet.http.HttpServletRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@Api(tags = ["관리자페이지 - 인증"])
class AdminAuthenticationController(
    private val adminService: AdminService
) {
    companion object {
        const val LOGIN_RESULT = "loginResult"
    }
    @ApiOperation("관리자 로그인")
    @PostMapping(WebUris.Admin.LOGIN)
    fun login(
        @RequestParam adminPassword: String,
        model: Model,
        request: HttpServletRequest
    ): String {
        if (!adminService.login(request, adminPassword)) {
            model[LOGIN_RESULT] = false
            return StaticPages.ADMIN_LOGIN
        }
        return StaticPages.Redirect.ADMIN_MAIN
    }

    @ApiOperation("관리자 로그아웃")
    @PostMapping(WebUris.Admin.LOGOUT)
    fun logout(request: HttpServletRequest): String {
        adminService.logout(request)
        return StaticPages.Redirect.ADMIN
    }
}
