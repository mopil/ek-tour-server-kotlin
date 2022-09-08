package com.ektour.entity

import javax.persistence.Column
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
    var businessName: String,
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
)