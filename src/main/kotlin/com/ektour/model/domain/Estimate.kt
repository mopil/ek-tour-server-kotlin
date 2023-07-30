package com.ektour.model.domain

import com.ektour.api.dto.CreateUpdateEstimateRequest
import com.ektour.model.PaymentMethods
import com.ektour.model.PaymentsMethodsConverter
import com.ektour.model.TaxBillYesOrNo
import com.ektour.model.TaxBillYesOrNoConverter
import com.ektour.model.TravelType
import com.ektour.model.VehicleType
import com.ektour.model.VehicleTypeConverter
import com.ektour.model.WayType
import com.ektour.model.WayTypeConverter
import com.ektour.web.dto.EstimateDetailDto
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
class Estimate(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val ip: String,
    name: String,
    email: String,
    phone: String,
    password: String,
    travelType: TravelType,
    vehicleType: VehicleType,
    vehicleNumber: Int,
    memberCount: Int,
    departDate: String,
    arrivalDate: String,
    departPlace: String,
    arrivalPlace: String,
    memo: String,
    stopPlace: String = "",
    wayType: WayType = WayType.ROUND_TRIP,
    payment: PaymentMethods = PaymentMethods.CASH,
    taxBillYesOrNo: TaxBillYesOrNo = TaxBillYesOrNo.NO,
) {
    var name: String = name
        protected set
    var email: String = email
        protected set
    var phone: String = phone
        protected set
    var password: String = password
        protected set

    var travelType: TravelType = travelType
        protected set
    @Convert(converter = VehicleTypeConverter::class)
    var vehicleType: VehicleType = vehicleType
        protected set
    var vehicleNumber: Int = vehicleNumber
        protected set
    var memberCount: Int = memberCount
        protected set
    var departDate: String = departDate
        protected set
    var arrivalDate: String = arrivalDate
        protected set
    var departPlace: String = departPlace
        protected set
    var arrivalPlace: String = arrivalPlace
        protected set
    var memo: String = memo
        protected set

    var stopPlace: String = stopPlace
        protected set
    @Convert(converter = WayTypeConverter::class)
    var wayType: WayType = wayType
        protected set

    @Convert(converter = PaymentsMethodsConverter::class)
    var payment: PaymentMethods = payment
        protected set
    @Convert(converter = TaxBillYesOrNoConverter::class)
    var taxBill: TaxBillYesOrNo = taxBillYesOrNo
        protected set

    var visibility: Boolean = true
        protected set
    var createdDate: String = ""
        protected set
    var validDate: String = ""
        protected set
    @PrePersist
    fun onPrePersist() {
        val stringFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val today = LocalDateTime.now()
        createdDate = today.format(stringFormat)
        validDate = today.plusDays(7).format(stringFormat)
    }

    fun softDelete() {
        visibility = false
    }

    fun updateByFrontend(
        request: CreateUpdateEstimateRequest
    ) {
        name = request.name
        email = request.email
        phone = request.phone
        password = request.password
        travelType = TravelType.from(request.travelType)
        vehicleType = VehicleType.fromKor(request.vehicleType)
        vehicleNumber = request.vehicleNumber
        memberCount = request.memberCount
        departDate = request.departDate
        arrivalDate = request.arrivalDate
        departPlace = request.departPlace
        arrivalPlace = request.arrivalPlace
        memo = request.memo
        stopPlace = request.stopPlace ?: ""
        wayType = WayType.fromKor(request.wayType)
        payment = PaymentMethods.fromKor(request.payment)
        taxBill = TaxBillYesOrNo.fromKor(request.taxBill)
    }

    fun updateByAdmin(form: EstimateDetailDto) {
        name = form.name
        email = form.email
        phone = form.phone
        password = form.password
        travelType = TravelType.from(form.travelType)
        vehicleType = VehicleType.fromKor(form.vehicleType)
        vehicleNumber = form.vehicleNumber
        memberCount = form.memberCount
        departDate = form.departDate
        arrivalDate = form.arrivalDate
        departPlace = form.departPlace
        arrivalPlace = form.arrivalPlace
        memo = form.memo
        stopPlace = form.stopPlace
        wayType = WayType.fromKor(form.wayType)
        payment = PaymentMethods.fromKor(form.payment)
        taxBill = TaxBillYesOrNo.fromKor(form.taxBill)
        visibility = form.visibility
    }
}
