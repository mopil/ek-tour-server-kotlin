package com.ektour.entity

import com.ektour.dto.CompanyInfoDto
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Admin(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var password: String,

    var name: String,
    var infoHandlerName: String,
    var businessNum: String,
    var registrationNum: String,
    var address: String,
    var tel: String,
    var fax: String,
    var phone: String,
    var email: String,
    var accountBank: String,
    var accountNum: String,
    var accountHolder: String,
    var kakaoTalkId: String,
) {
    fun updatePassword(new: String) { this.password = new }
    fun updateCompanyInfo(form: CompanyInfoDto) {
        name = form.adminName
        infoHandlerName = form.infoHandlerName
        businessNum = form.businessNum
        registrationNum = form.registrationNum
        address = form.address
        tel = form.tel
        fax = form.fax
        phone = form.phone
        email = form.email
        accountBank = form.accountBank
        accountNum = form.accountNum
        accountHolder = form.accountHolder
        kakaoTalkId = form.kakaoTalkId
    }
}