package com.ektour.model.domain

import com.ektour.web.dto.CompanyInfoDto
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Admin(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    password: String,
    name: String,
    infoHandlerName: String,
    businessNum: String,
    registrationNum: String,
    address: String,
    tel: String,
    fax: String,
    phone: String,
    email: String,
    accountBank: String,
    accountNum: String,
    accountHolder: String,
    kakaoTalkId: String,
) {
    var password: String = password
        protected set
    var name: String = name
        protected set
    var infoHandlerName: String = infoHandlerName
        protected set
    var businessNum: String = businessNum
        protected set
    var registrationNum: String = registrationNum
        protected set
    var address: String = address
        protected set
    var tel: String = tel
        protected set
    var fax: String = fax
        protected set
    var phone: String = phone
        protected set
    var email: String = email
        protected set
    var accountBank: String = accountBank
        protected set
    var accountNum: String = accountNum
        protected set
    var accountHolder: String = accountHolder
        protected set
    var kakaoTalkId: String = kakaoTalkId
        protected set
    fun updatePassword(newPassword: String) {
        password = newPassword
    }
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
