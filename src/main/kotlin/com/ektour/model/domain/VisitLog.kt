package com.ektour.model.domain

import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class VisitLog(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val device: String,
    val ip: String,
    val visitedAt: LocalDateTime = LocalDateTime.now(),
    val visitedDate: String = LocalDate.now().toString(),
)
