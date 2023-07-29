package com.ektour.model

@Suppress("EnumEntryName", "NonAsciiCharacters")
enum class TravelType {

    일반여행, 관혼상제, 학교단체, 기타단체;
    companion object {
        fun from(kor: String?): TravelType {
            return when (kor) {
                "관혼상제" -> 관혼상제
                "학교단체" -> 학교단체
                "기타단체" -> 기타단체
                else -> 일반여행
            }
        }
    }
}

enum class VehicleType(val kor: String) {
    SMALL("25인승 소형"),
    LIMOUSINE("28인승 리무진"),
    LARGE("45인승 대형");

    companion object {
        fun fromKor(kor: String?): VehicleType {
            return when (kor) {
                "28인승 리무진" -> LIMOUSINE
                "45인승 대형" -> LARGE
                else -> SMALL
            }
        }
    }
}

enum class WayType(val kor: String) {
    ROUND_TRIP("왕복"),
    ONE_WAY("편도");

    companion object {
        fun fromKor(kor: String?): WayType {
            return when (kor) {
                "편도" -> ONE_WAY
                else -> ROUND_TRIP
            }
        }
    }
}

enum class PaymentMethods(val kor: String) {
    CASH("현금"),
    CARD("카드");
    companion object {
        fun fromKor(kor: String?): PaymentMethods {
            return when (kor) {
                "카드" -> CARD
                else -> CASH
            }
        }
    }
}

enum class TaxBillYesOrNo(val kor: String) {
    YES("발급"),
    NO("발급안함");
    companion object {
        fun fromKor(kor: String?): TaxBillYesOrNo {
            return when (kor) {
                "발급" -> YES
                else -> NO
            }
        }
    }
}
