package com.ektour.model.domain

import org.springframework.data.jpa.repository.JpaRepository

interface VisitLogRepository : JpaRepository<VisitLog, Long> {
    fun countAllByVisitedDate(stringDate: String): Long
}
