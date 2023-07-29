package com.ektour.utils

import com.ektour.web.dto.AdminSearchForm
import java.util.concurrent.ConcurrentHashMap
import org.springframework.stereotype.Component

@Component
class AdminSearchFormStorage {
    private val searchStorage: MutableMap<String, AdminSearchForm> = ConcurrentHashMap()
    private val defaultKey = "adminSearchForm"
    fun set(form: AdminSearchForm) = searchStorage.put(defaultKey, form)
    fun get(): AdminSearchForm = searchStorage[defaultKey] ?: AdminSearchForm()
}
