package com.ektour.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Visit(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    var today: Int = 0,
    var total: Int = 0,
) {
    fun visit() { today += 1; total += 1 }
}
