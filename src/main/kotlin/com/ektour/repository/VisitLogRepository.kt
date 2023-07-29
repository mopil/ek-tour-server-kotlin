package com.ektour.repository

import com.ektour.entity.VisitLog
import org.springframework.data.jpa.repository.JpaRepository

interface VisitLogRepository : JpaRepository<VisitLog, Long> {
    fun countAllByVisitedDate(stringDate: String): Long
}
