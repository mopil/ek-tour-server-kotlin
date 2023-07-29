package com.ektour.web.controller

import com.ektour.common.auth.AdminAuthenticate
import com.ektour.service.AdminService
import com.ektour.service.VisitLogService
import com.ektour.web.dto.CompanyInfoDto
import com.ektour.web.dto.UpdateAdminPasswordForm
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import javax.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Controller
@RequestMapping("/admin")
@Api(tags = ["관리자페이지 - 정보 API"])
@AdminAuthenticate
class AdminInfoController(
    private val adminService: AdminService,
    private val visitLogService: VisitLogService
) {
    @ApiOperation("관리자 설정 화면 조회")
    @GetMapping("/setting")
    fun settingPage(model: Model): String {
        model["infoForm"] = adminService.getCompanyInfo()
        model["pwForm"] = UpdateAdminPasswordForm()
        model["visitToday"] = visitLogService.getTodayVisitCount()
        model["visitTotal"] = visitLogService.getTotalVisitCount()
        return "settingPage"
    }

    @ApiOperation("관리자 정보 변경")
    @PostMapping("/setting/info")
    fun updateCompanyInfo(@ModelAttribute("infoForm") companyInfo: CompanyInfoDto): String {
        adminService.updateCompanyInfo(companyInfo)
        return "redirect:/admin/setting"
    }

    @ApiOperation("관리자 비밀번호 변경")
    @PostMapping("/setting/password")
    fun updateAdminPassword(
        @Valid @ModelAttribute("pwForm") passwordForm: UpdateAdminPasswordForm
    ): String {
        adminService.updatePassword(passwordForm)
        return "redirect:/admin/setting"
    }

    @ApiOperation("회사 로고 업로드")
    @PostMapping("/logo")
    fun updateLogo(@ModelAttribute("file") file: MultipartFile): String {
        adminService.updateLogo(file)
        return "redirect:/admin/setting"
    }
}
