package com.ektour.common

import com.ektour.model.domain.Admin
import com.ektour.model.domain.AdminRepository
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class AdminGenerator(
    private val adminRepository: AdminRepository
) {
    @PostConstruct
    fun createDefaultAdmin() {
        if (adminRepository.count() == 0L) {
            adminRepository.save(
                Admin(
                    id = 1L,
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
                    "seanpapa"
                )
            )
        }
    }
}
