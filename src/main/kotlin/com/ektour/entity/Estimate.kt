package com.ektour.entity

import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
class Estimate(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    // 신청자 정보
    var name: String,
    var email: String,
    var phone: String,
    var password: String,

    // 필수 견적 요청
    var travelType: String,
    var vehicleType: String,
    var vehicleNumber: Int,
    var memberCount: Int,
    var departDate: String,
    var arrivalDate: String,
    var departPlace: String,
    var arrivalPlace: String,
    var memo: String,

    var visibility: Boolean = true,

    var createdDate: String,
    var validDate: String, // 견적 요청일로 부터 +7일
    var ip: String,
) {
    @PrePersist
    fun onPrePersist() {
        createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        validDate = LocalDateTime.now().plusDays(7).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    }
}