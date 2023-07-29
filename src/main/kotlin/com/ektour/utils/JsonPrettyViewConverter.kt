package com.ektour.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import org.springframework.stereotype.Component

@Component
class JsonPrettyViewConverter {
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
    private val jsonParser = JsonParser()

    fun convert(obj: ByteArray): String {
        return gson.toJson(jsonParser.parse(String(obj, Charsets.UTF_8)))
    }
}
