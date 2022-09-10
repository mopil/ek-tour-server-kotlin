package com.ektour.utils

import com.ektour.dto.AdminSearchForm
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class SearchStorage {
    private val searchStorage: MutableMap<String, AdminSearchForm> = ConcurrentHashMap()
    fun setValue(key: String, form: AdminSearchForm) = searchStorage.put(key, form)
    fun getValue(key: String): AdminSearchForm = searchStorage[key] ?: AdminSearchForm()
}