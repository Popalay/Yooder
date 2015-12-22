package com.popalay.yooder.models

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseUser
import java.util.*

@ParseClassName("Debt")
class Debt() : ParseObject() {
    var author: ParseUser
        get() = getParseUser("author")
        set(value) = put("author", value)
    var isDebtor: Boolean
        get() = getBoolean("isDebtor")
        set(value) = put("isDebtor", value)
    var title: String
        get() = getString("title")
        set(value) = put("title", value)
    var party: ParseUser
        get() = getParseUser("party")
        set(value) = put("party", value)
    var partyAnon: String
        get() = getString("partyAnon")
        set(value) = put("partyAnon", value)
    var amount: Double
        get() = getDouble("amount")
        set(value) = put("amount", value)
    var date: Date
        get() = getDate("date")
        set(value) = put("date", value)
}
