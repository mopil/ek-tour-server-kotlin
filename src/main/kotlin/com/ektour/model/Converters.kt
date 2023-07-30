package com.ektour.model

import javax.persistence.AttributeConverter

class PaymentsMethodsConverter : AttributeConverter<PaymentMethods, String> {
    override fun convertToDatabaseColumn(attribute: PaymentMethods?): String {
        return attribute?.kor ?: PaymentMethods.CASH.kor
    }

    override fun convertToEntityAttribute(dbData: String?): PaymentMethods {
        return PaymentMethods.fromKor(dbData)
    }
}

class TaxBillYesOrNoConverter : AttributeConverter<TaxBillYesOrNo, String> {
    override fun convertToDatabaseColumn(attribute: TaxBillYesOrNo?): String {
        return attribute?.kor ?: TaxBillYesOrNo.NO.kor
    }

    override fun convertToEntityAttribute(dbData: String?): TaxBillYesOrNo {
        return TaxBillYesOrNo.fromKor(dbData)
    }
}

class VehicleTypeConverter : AttributeConverter<VehicleType, String> {
    override fun convertToDatabaseColumn(attribute: VehicleType?): String {
        return attribute?.kor ?: VehicleType.SMALL.kor
    }

    override fun convertToEntityAttribute(dbData: String?): VehicleType {
        return VehicleType.fromKor(dbData)
    }
}

class WayTypeConverter : AttributeConverter<WayType, String> {
    override fun convertToDatabaseColumn(attribute: WayType?): String {
        return attribute?.kor ?: WayType.ROUND_TRIP.kor
    }

    override fun convertToEntityAttribute(dbData: String?): WayType {
        return WayType.fromKor(dbData)
    }
}

class TravelTypeConverter : AttributeConverter<TravelType, String> {
    override fun convertToDatabaseColumn(attribute: TravelType?): String {
        return attribute?.name ?: TravelType.일반여행.name
    }

    override fun convertToEntityAttribute(dbData: String?): TravelType {
        return TravelType.from(dbData)
    }
}
