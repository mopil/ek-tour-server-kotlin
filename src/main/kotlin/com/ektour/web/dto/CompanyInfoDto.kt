package com.ektour.web.dto

import com.ektour.model.domain.Admin

data class CompanyInfoDto(
    val adminName: String,
    val infoHandlerName: String,
    val businessNum: String,
    val registrationNum: String,
    val address: String,
    val tel: String,
    val fax: String,
    val phone: String,
    val email: String,
    val accountBank: String,
    val accountNum: String,
    val accountHolder: String,
    val kakaoTalkId: String
) {
    constructor(entity: Admin) : this(
        adminName = entity.name,
        infoHandlerName = entity.infoHandlerName,
        businessNum = entity.businessNum,
        registrationNum = entity.registrationNum,
        address = entity.address,
        tel = entity.tel,
        fax = entity.fax,
        phone = entity.phone,
        email = entity.email,
        accountBank = entity.accountBank,
        accountNum = entity.accountNum,
        accountHolder = entity.accountHolder,
        kakaoTalkId = entity.kakaoTalkId
    )
}