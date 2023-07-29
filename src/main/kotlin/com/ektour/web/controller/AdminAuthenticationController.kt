package com.ektour.web.controller

import com.ektour.service.AdminService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import javax.servlet.http.HttpServletRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/admin")
@Api(tags = ["관리자페이지 - 인증 API"])
class AdminAuthenticationController(
    private val adminService: AdminService
) {
    @ApiOperation("관리자 로그인")
    @PostMapping("/login")
    fun login(
        @RequestParam("adminPassword") adminPassword: String,
        model: Model,
        request: HttpServletRequest
    ): String {
        if (!adminService.login(request, adminPassword)) {
            model["loginResult"] = false
            return "loginPage"
        }
        return "redirect:/admin/main"
    }

    @ApiOperation("관리자 로그아웃")
    @PostMapping("/logout")
    fun logout(request: HttpServletRequest): String {
        adminService.logout(request)
        return "redirect:/admin"
    }
}
