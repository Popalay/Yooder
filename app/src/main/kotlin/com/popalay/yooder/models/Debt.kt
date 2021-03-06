package com.popalay.yooder.models

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import org.json.JSONObject
import java.util.*

@ParseClassName("Debt")
class Debt() : ParseObject() {
    var author: ParseUser
        get() = getParseUser("author")
        set(value) = put("author", value)
    var isDebtor: Boolean
        get() = getBoolean("isDebtor")
        set(value) = put("isDebtor", value)
    var description: String
        get() = getString("description")
        set(value) = put("description", value)
    var party: String
        get() = getString("party")
        set(value) = put("party", value)
    var amount: Double
        get() = getDouble("amount")
        set(value) = put("amount", value)
    var date: Date?
        get() = if(getDate("date") != JSONObject.NULL) getDate("date")  else null
        set(value) = if(value != null) put("date", value) else put("date", JSONObject.NULL)

    companion object {
        fun getByAuthor(author: ParseUser): ParseQuery<Debt> {
            return ParseQuery<Debt>(Debt::class.java).whereEqualTo("author", author).orderByAscending("date")
        }
    }
}
