package com.ektour.web.dto

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import javax.validation.constraints.NotNull

data class AdminSearchForm(
    @field:NotNull
    @field:DateTimeFormat(pattern = "yyyy-MM-dd")
    var start: LocalDate = LocalDate.now(),

    @field:NotNull
    @field:DateTimeFormat(pattern = "yyyy-MM-dd")
    var end: LocalDate = LocalDate.now(),

    var searchType: String = "",
    var keyword: String = "",
)
