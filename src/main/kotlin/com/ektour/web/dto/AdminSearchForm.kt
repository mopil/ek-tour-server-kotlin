package com.ektour.web.dto

import java.time.LocalDate
import javax.validation.constraints.NotNull
import org.springframework.format.annotation.DateTimeFormat

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