package com.ektour.web

import com.ektour.service.VisitLogService
import org.springframework.stereotype.Component
import org.springframework.ui.Model
import org.springframework.ui.set

@Component
class ViewModelUtils(
    private val visitLogService: VisitLogService
) {
    fun addVisitCount(model: Model): Model {
        model["visitToday"] = visitLogService.getTodayVisitCount()
        model["visitTotal"] = visitLogService.getTotalVisitCount()
        return model
    }

}