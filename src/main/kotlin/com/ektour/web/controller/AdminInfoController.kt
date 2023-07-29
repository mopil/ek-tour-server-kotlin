package com.ektour.web.controller

import com.ektour.common.auth.AdminAuthenticate
import com.ektour.service.AdminService
import com.ektour.web.StaticPages
import com.ektour.web.ViewModelUtils
import com.ektour.web.WebUris
import com.ektour.web.dto.CompanyInfoDto
import com.ektour.web.dto.UpdateAdminPasswordForm
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid

@Controller
@Api(tags = ["관리자페이지 - 정보"])
@AdminAuthenticate
class AdminInfoController(
    private val adminService: AdminService,
    private val viewModelUtils: ViewModelUtils
) {
    companion object {
        const val INFO_FORM = "infoForm"
        const val PASSWORD_FORM = "pwForm"
        const val FILE = "file"
    }
    @ApiOperation("관리자 설정 화면 조회")
    @GetMapping(WebUris.Admin.GO_ADMIN_SETTING_PAGE)
    fun settingPage(model: Model): String {
        model[INFO_FORM] = adminService.getCompanyInfo()
        model[PASSWORD_FORM] = UpdateAdminPasswordForm()
        viewModelUtils.addVisitCount(model)
        return StaticPages.ADMIN_SETTING
    }

    @ApiOperation("관리자 정보 변경")
    @PostMapping(WebUris.Admin.UPDATE_ADMIN_INFO)
    fun updateAdminInfo(
        @ModelAttribute(INFO_FORM) companyInfo: CompanyInfoDto
    ): String {
        adminService.updateCompanyInfo(companyInfo)
        return StaticPages.Redirect.ADMIN_SETTING
    }

    @ApiOperation("관리자 비밀번호 변경")
    @PostMapping(WebUris.Admin.UPDATE_ADMIN_PASSWORD)
    fun updateAdminPassword(
        @Valid @ModelAttribute(PASSWORD_FORM) passwordForm: UpdateAdminPasswordForm
    ): String {
        adminService.updatePassword(passwordForm)
        return StaticPages.Redirect.ADMIN_SETTING
    }

    @ApiOperation("회사 로고 업로드")
    @PostMapping(WebUris.Admin.UPDATE_ADMIN_COMPANY_LOGO)
    fun updateLogo(@ModelAttribute(FILE) file: MultipartFile): String {
        adminService.updateLogo(file)
        return StaticPages.Redirect.ADMIN_SETTING
    }
}
