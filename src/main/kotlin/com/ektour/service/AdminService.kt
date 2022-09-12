package com.ektour.service

import com.ektour.repository.AdminRepository
import com.ektour.common.ADMIN
import com.ektour.common.AdminException
import com.ektour.common.LINUX_LOGO_PATH
import com.ektour.common.logger
import com.ektour.dto.CompanyInfoDto
import com.ektour.dto.UpdateAdminPasswordForm
import com.ektour.entity.Admin
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import javax.servlet.http.HttpServletRequest

@Service
@Transactional
class AdminService(private val adminRepository: AdminRepository) {

    val log = logger()

    fun getAdmin(): Admin = adminRepository.findById(1L)
        .orElseThrow { throw AdminException("관리자 조회 실패") }

    fun login(request: HttpServletRequest, password: String): Boolean {
        if (!adminRepository.existsAdminByPassword(password)) return false
        request.session.setAttribute(ADMIN, getAdmin())
        return true
    }

    fun logout(request: HttpServletRequest) = request.session.invalidate()

    fun updatePassword(form: UpdateAdminPasswordForm) {
        if (getAdmin().password != form.nowPassword)
            throw AdminException("현재 비밀번호가 틀립니다.")
        if (!form.isSamePassword())
            throw AdminException("새로운 비밀번호를 다시 확인 해주세요.")
        getAdmin().updatePassword(form.newPassword)
    }

    fun getCompanyInfo() = CompanyInfoDto(getAdmin())

    fun updateCompanyInfo(form: CompanyInfoDto) = getAdmin().updateCompanyInfo(form)

    fun updateLogo(file: MultipartFile) {
        try { file.transferTo(File(LINUX_LOGO_PATH)) }
        catch (e: IOException) {
            log.warn("파일 업로드 에러 : {}", e.message)
            throw AdminException("로고 변경 오류.")
        }
    }
}