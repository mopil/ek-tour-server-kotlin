package com.ektour.service

import com.ektour.api.util.IpExtractor.getIp
import com.ektour.common.client.SlackClient
import com.ektour.model.domain.VisitLog
import com.ektour.model.domain.VisitLogRepository
import org.springframework.mobile.device.DeviceUtils
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest

@Component
@Transactional
class VisitLogService(
    private val visitLogRepository: VisitLogRepository,
    private val slackClient: SlackClient
) {
    fun getTodayVisitCount(): Long {
        val todayString = LocalDateTime.now().toLocalDate().toString()
        return visitLogRepository.countAllByVisitedDate(todayString)
    }
    fun getTotalVisitCount(): Long {
        return visitLogRepository.count()
    }

    fun saveVisitLog(request: HttpServletRequest) {
        val device = DeviceUtils.getCurrentDevice(request)?.devicePlatform?.name ?: "UNKNOWN"
        val ip = request.getIp()
        visitLogRepository.save(VisitLog(device = device, ip = ip))
    }

    @Scheduled(cron = "59 23 * * * *")
    fun sendTodayVisitLogCount() {
        val message = """
            [${LocalDate.now()}]
            오늘 방문자 수: ${getTodayVisitCount()}명
            총 방문자 수: ${getTotalVisitCount()}명
        """.trimIndent()
        val channel = "visit-log"
        slackClient.sendSlack(message = message, channel = channel)
    }
}
