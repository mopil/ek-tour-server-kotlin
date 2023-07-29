package com.ektour.api.dto

import com.ektour.model.domain.Estimate

data class GetEstimateSimpleResponse(
    val id: Long,
    val name: String,
    val travelType: String,
    val departPlace: String,
    val arrivalPlace: String,
    val vehicleType: String,
    val createdDate: String,
) {
    constructor(entity: Estimate) : this(
        id = entity.id,
        name = entity.name,
        travelType = entity.travelType.name,
        departPlace = entity.departPlace,
        arrivalPlace = entity.arrivalPlace,
        vehicleType = entity.vehicleType.kor,
        createdDate = entity.createdDate
    )
}
