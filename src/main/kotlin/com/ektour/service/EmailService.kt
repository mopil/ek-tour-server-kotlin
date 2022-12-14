package com.ektour.service

import com.ektour.common.ADMIN_EMAIL_ACCOUNT
import com.ektour.common.BoolResponse
import com.ektour.common.EmailException
import com.ektour.dto.EstimateForm
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import javax.mail.Message
import javax.mail.internet.InternetAddress

@Service
class EmailService(private val mailSender: JavaMailSender) {
    @Async
    fun sendMail(form: EstimateForm): BoolResponse {
        try {
            val message = mailSender.createMimeMessage()
            message.addRecipients(Message.RecipientType.TO, ADMIN_EMAIL_ACCOUNT)
            message.subject = "[이케이하나관광 견적요청]"
            var text = ""
            text += "${form.name} ${form.phone}\n"
            text += "${form.travelType} ${form.vehicleType} ${form.vehicleNumber}대\n"
            text += "${form.departPlace} ~ ${form.arrivalPlace}\n"
            text += "경유지(${form.stopPlace})\n"
            text += "${form.departDate} ~ ${form.arrivalDate}\n"
            message.setText(text, "utf-8")
            message.setFrom(InternetAddress(ADMIN_EMAIL_ACCOUNT, form.name))
            mailSender.send(message)
            return BoolResponse(true)
        } catch (e: Exception) {
            throw EmailException("이메일 전송 관련 오류 발생")
        }
    }
}