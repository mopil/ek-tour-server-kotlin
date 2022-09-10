package com.ektour.controller

import com.ektour.common.auth.Auth
import com.ektour.dto.CompanyInfoDto
import com.ektour.dto.UpdateAdminPasswordForm
import com.ektour.service.AdminService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@Controller
@RequestMapping("/admin")
class AdminInfoController(private val adminService: AdminService) {

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

    @Auth @GetMapping
    fun welcomePage() = "redirect:/admin/main"

    @PostMapping("/logout")
    fun logout(request: HttpServletRequest): String {
        adminService.logout(request)
        return "redirect:/admin"
    }

    @Auth @GetMapping("/setting")
    fun settingPage(model: Model): String {
        model["infoForm"] = adminService.getCompanyInfo()
        model["pwForm"] = UpdateAdminPasswordForm()
        return "settingPage"
    }

    // 관리자 정보 변경
    @Auth @PostMapping("/setting/info")
    fun updateCompanyInfo(@ModelAttribute("infoForm") companyInfo: CompanyInfoDto): String {
        adminService.updateCompanyInfo(companyInfo)
        return "redirect:/admin/setting"
    }

    // 관리자 비밀번호 변경
    @Auth @PostMapping("/setting/password")
    fun updateAdminPassword(
        @Valid @ModelAttribute("pwForm") passwordForm: UpdateAdminPasswordForm
    ): String {
        if (passwordForm.isSamePassword()) {
            adminService.updatePassword(passwordForm.newPassword)
        }
        return "redirect:/admin/setting"
    }

    // 클라이언트로 어드민 정보(회사 정보) 내려주기
    @ResponseBody
    @GetMapping("/info")
    fun getAdminInfo(): CompanyInfoDto = adminService.getCompanyInfo()

    // 회사 로고 업로드
    @Auth @PostMapping("/logo")
    fun updateLogo(@ModelAttribute("file") file: MultipartFile): String {
        adminService.updateLogo(file)
        return "redirect:/admin/setting"
    }

}