package com.ektour.api.dto

import com.ektour.model.domain.Admin

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

/**
 * 클라이언트 응답
 */
data class PageTotalCountResponse(
    var totalCount: Int = 0
)

data class EstimateSimpleResponse(
    var id: Long,
    var name: String,
    var travelType: String,
    var departPlace: String,
    var arrivalPlace: String,
    var vehicleType: String,
    var createdDate: String,
)

data class EstimatePagedResponse(
    var currentPage: Int,
    var totalPage: Int,
    var currentPageCount: Int,
    var totalCount: Int,
    var estimateList: List<EstimateSimpleResponse>,
)

data class EstimateDetailDto(
    var id: Long,

    // 신청자 정보
    var name: String,
    var email: String,
    var phone: String,
    var password: String,

    // 필수
    var travelType: String, // 일반여행, 관혼상제, 학교단체, 기타단체
    var vehicleType: String, // 25인승 소형, 28인승 리무진, 45인승 대형
    var vehicleNumber: Int, // 차량대수
    var memberCount: Int, // 인원수
    var departDate: String, // 출발일시
    var arrivalDate: String, // 도착일시
    var departPlace: String, // 출발지
    var arrivalPlace: String, // 도착지
    var memo: String, // 기타메모

    // 선택
    var stopPlace: String, // 경유지
    var wayType: String, // 왕복구분 : 왕복, 편도
    var payment: String, // 결제방법 : 현금, 카드
    var taxBill: String, // 세금계산서 : 발급, 발급안함

    // 견적 요청 삭제여부 (사용자에게 보여지는 여부)
    var visibility: Boolean = true,

    var createdDate: String, // 견적 요청일

    var ip: String,
)
