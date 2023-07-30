package com.ektour.service

import com.ektour.api.dto.CreateUpdateEstimateRequest
import com.ektour.common.AdminConstants.ADMIN_EMAIL_ACCOUNT
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import javax.mail.internet.InternetAddress

@Service
class EmailService(private val mailSender: JavaMailSender) {
    @Async
    fun sendMail(form: CreateUpdateEstimateRequest) {
        val message = MimeMessageHelper(mailSender.createMimeMessage(), "utf-8")
        message.setTo(ADMIN_EMAIL_ACCOUNT)
        message.setSubject("[이케이하나관광 견적요청]")

        message.setText(
            """
            ${form.name} ${form.phone}
            ${form.travelType} ${form.vehicleType} ${form.vehicleNumber}대
            ${form.departPlace} ~ ${form.arrivalPlace}
            경유지(${form.stopPlace})
            ${form.departDate.replace("T", " ")} ~ ${form.arrivalDate.replace("T", " ")}
            ${form.memo}
            """.trimIndent()
        )

        message.setFrom(InternetAddress(ADMIN_EMAIL_ACCOUNT, form.name))
        mailSender.send(message.mimeMessage)
    }
}
