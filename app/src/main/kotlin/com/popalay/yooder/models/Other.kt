package com.popalay.yooder.models

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import org.json.JSONObject
import java.util.*

@ParseClassName("Other")
class Other() : ParseObject() {
    var author: ParseUser
        get() = getParseUser("author")
        set(value) = put("author", value)
    var title: String
        get() = getString("title")
        set(value) = put("title", value)
    var description: String
        get() = getString("description")
        set(value) = put("description", value)
    var date: Date?
        get() = if(getDate("date") != JSONObject.NULL) getDate("date")  else null
        set(value) = if(value != null) put("date", value) else put("date", JSONObject.NULL)

    companion object {
        fun getByAuthor(author: ParseUser): ParseQuery<Other> {
            return ParseQuery<Other>(Other::class.java).whereEqualTo("author", author).orderByAscending("date")
        }
    }
}