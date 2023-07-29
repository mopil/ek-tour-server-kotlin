package com.ektour.service

import com.ektour.common.logger
import com.ektour.repository.VisitRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class VisitService(private val visitRepository: VisitRepository) {
    val log = logger()

    private fun getVisit() = visitRepository.findById(1L).get()

    fun getToday() = getVisit().today
    fun getTotal() = getVisit().total

    // today += 1 && total += 1
    fun visit() = getVisit().visit()

    // 매일 오전 12시에 today 방문자 초기화
    @Scheduled(cron = "0 24 * * * ?")
    fun resetTodayVisits() {
        val visitor = getVisit()
        log.info("투데이 방문자 통계 초기화 today = {}, total = {}", visitor.today, visitor.total)
        visitor.today = 0
    }
}
