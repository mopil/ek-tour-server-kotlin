package com.ektour.web.util

import com.ektour.web.dto.AdminSearchForm
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class AdminSearchFormStorage {
    private val searchStorage: MutableMap<String, AdminSearchForm> = ConcurrentHashMap()
    private val defaultKey = "adminSearchForm"
    fun set(form: AdminSearchForm) = searchStorage.put(defaultKey, form)
    fun get(): AdminSearchForm = searchStorage[defaultKey] ?: AdminSearchForm()
}
