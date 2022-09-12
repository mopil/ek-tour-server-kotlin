package com.ektour

import com.ektour.entity.Admin
import com.ektour.entity.Visit
import com.ektour.repository.AdminRepository
import com.ektour.repository.VisitRepository
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class InitDummyData(
    private val adminRepository: AdminRepository,
    private val visitRepository: VisitRepository
    ) {
    @PostConstruct
    fun init() {
        adminRepository.save(Admin(
            id = null,
            "1234",
            "배승원",
            "조운",
            "324-87-00192",
            "284911-0144524",
            "서울 송파구 가락로 102 르호봇 408호",
            "02.3432.6545",
            "02.6008.6545",
            "010-6387-6086",
            "ektour0914@naver.com",
            "KB 국민은행",
            "810137-04-006627",
            "이케이투어",
            "seanpapa"))
        visitRepository.save(Visit(id = null))
    }
}