package com.ektour.web.dto

import com.ektour.model.domain.Estimate

data class EstimateDetailDto(
    val id: Long,
    val name: String,
    val email: String,
    val phone: String,
    val password: String,
    val travelType: String,
    val vehicleType: String,
    val vehicleNumber: Int,
    val memberCount: Int,
    val departDate: String,
    val arrivalDate: String,
    val departPlace: String,
    val arrivalPlace: String,
    val memo: String,
    val stopPlace: String,
    val wayType: String,
    val payment: String,
    val taxBill: String,
    val visibility: Boolean = true,
    val createdDate: String,
    val ip: String,
) {
    constructor(entity: Estimate) : this (
        id = entity.id,
        name = entity.name,
        email = entity.email,
        phone = entity.phone,
        password = entity.password,
        travelType = entity.travelType.name,
        vehicleType = entity.vehicleType.kor,
        vehicleNumber = entity.vehicleNumber,
        memberCount = entity.memberCount,
        departDate = entity.departDate,
        arrivalDate = entity.arrivalDate,
        departPlace = entity.departPlace,
        arrivalPlace = entity.arrivalPlace,
        memo = entity.memo,
        stopPlace = entity.stopPlace,
        wayType = entity.wayType.kor,
        payment = entity.payment.kor,
        taxBill = entity.taxBill.kor,
        visibility = entity.visibility,
        createdDate = entity.createdDate,
        ip = entity.ip
    )
}
