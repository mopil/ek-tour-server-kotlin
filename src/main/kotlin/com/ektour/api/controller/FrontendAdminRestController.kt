package com.ektour.api.controller

import com.ektour.api.Uris
import com.ektour.service.AdminService
import com.ektour.web.dto.CompanyInfoDto
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Api(tags = ["프론트엔드 - 관리자 정보 REST API"])
class FrontendAdminRestController(
    private val adminService: AdminService,
) {
    @ApiOperation("관리자 정보 조회")
    @GetMapping(Uris.AdminApis.GET_ADMIN_INFO)
    fun getAdminInfo(): CompanyInfoDto = adminService.getCompanyInfo()
}
