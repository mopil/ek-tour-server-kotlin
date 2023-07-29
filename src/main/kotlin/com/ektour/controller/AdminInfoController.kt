package com.ektour.controller

import com.ektour.common.auth.Auth
import com.ektour.dto.CompanyInfoDto
import com.ektour.dto.UpdateAdminPasswordForm
import com.ektour.service.AdminService
import com.ektour.service.VisitLogService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import springfox.documentation.annotations.ApiIgnore
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@Controller
@RequestMapping("/admin")
@Api(tags = ["관리자페이지 - 정보 API"])
class AdminInfoController(
    private val adminService: AdminService,
    private val visitLogService: VisitLogService
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

    @ApiIgnore
    @Auth @GetMapping
    fun welcomePage() = "redirect:/admin/main"

    @ApiOperation("관리자 로그아웃")
    @PostMapping("/logout")
    fun logout(request: HttpServletRequest): String {
        adminService.logout(request)
        return "redirect:/admin"
    }

    @ApiOperation("관리자 설정 화면 조회")
    @Auth @GetMapping("/setting")
    fun settingPage(model: Model): String {
        model["infoForm"] = adminService.getCompanyInfo()
        model["pwForm"] = UpdateAdminPasswordForm()
        model["visitToday"] = visitLogService.getTodayVisitCount()
        model["visitTotal"] = visitLogService.getTotalVisitCount()
        return "settingPage"
    }

    @ApiOperation("관리자 정보 변경")
    @Auth @PostMapping("/setting/info")
    fun updateCompanyInfo(@ModelAttribute("infoForm") companyInfo: CompanyInfoDto): String {
        adminService.updateCompanyInfo(companyInfo)
        return "redirect:/admin/setting"
    }

    @ApiOperation("관리자 비밀번호 변경")
    @Auth @PostMapping("/setting/password")
    fun updateAdminPassword(
        @Valid @ModelAttribute("pwForm") passwordForm: UpdateAdminPasswordForm
    ): String {
        adminService.updatePassword(passwordForm)
        return "redirect:/admin/setting"
    }

    @ApiOperation("관리자 정보 조회")
    @ResponseBody
    @GetMapping("/info")
    fun getAdminInfo(): CompanyInfoDto = adminService.getCompanyInfo()

    @ApiOperation("회사 로고 업로드")
    @Auth @PostMapping("/logo")
    fun updateLogo(@ModelAttribute("file") file: MultipartFile): String {
        adminService.updateLogo(file)
        return "redirect:/admin/setting"
    }
}
