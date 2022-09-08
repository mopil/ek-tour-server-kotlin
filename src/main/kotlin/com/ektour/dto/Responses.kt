package com.ektour.dto

import com.ektour.entity.Admin

data class CompanyInfoDto(
    var adminName: String,
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
    var kakaoTalkId: String
) {
    constructor(a: Admin) : this(
        adminName = a.name,
        infoHandlerName = a.infoHandlerName,
        businessNum = a.businessNum,
        registrationNum = a.registrationNum,
        address = a.address,
        tel = a.tel,
        fax = a.fax,
        phone = a.phone,
        email = a.email,
        accountBank = a.accountBank,
        accountNum = a.accountNum,
        accountHolder = a.accountHolder,
        kakaoTalkId = a.kakaoTalkId
    )
}