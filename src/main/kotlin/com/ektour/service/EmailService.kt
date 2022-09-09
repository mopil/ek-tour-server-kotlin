package com.ektour.service

import com.ektour.common.ADMIN_EMAIL_ACCOUNT
import com.ektour.common.EmailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import javax.mail.Message
import javax.mail.internet.InternetAddress

@Service
class EmailService(private val mailSender: JavaMailSender) {
    @Async
    fun sendMail(form: EstimateRequest) {
        try {
            val message = mailSender.createMimeMessage()
            message.addRecipients(Message.RecipientType.TO, ADMIN_EMAIL_ACCOUNT)
            message.subject = "[이케이하나관광 견적요청]"
            var text = ""
            text += (form.getName() + " " + form.getPhone()).toString() + "\n"
            text += ((form.getTravelType() + " " + form.getVehicleType()).toString() + " " + form.getVehicleNumber()).toString() + "대\n"
            text += (form.getDepartPlace() + " ~ " + form.getArrivalPlace()).toString() + "\n"
            text += """${"경유지(" + form.getStopPlace()})""".trimIndent()
            text += (form.getDepartDate() + " ~ " + form.getArrivalDate()).toString() + "\n"
            message.setText(text, "utf-8")
            message.setFrom(InternetAddress(ADMIN_EMAIL_ACCOUNT, form.getName()))
            mailSender.send(message)
        } catch (e: Exception) {
            throw EmailException("이메일 전송 관련 오류 발생")
        }
    }
}