package com.popalay.yooder.models

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseUser
import java.util.*

enum class Currency(val currency: String) {
    UAH("₴"), RUB("руб"), EUR("€"), USD("$"), GBP("£")
}

@ParseClassName("Debt")
class Debt() : ParseObject() {
    var author: ParseUser
        get() = getParseUser("author")
        set(value) = put("author", value)
    var title: String
        get() = getString("title")
        set(value) = put("title", value)
    var debtor: ParseUser
        get() = getParseUser("debtor")
        set(value) = put("debtor", value)
    var debtorStr: String
        get() = getString("debtorStr")
        set(value) = put("debtorStr", value)
    var amount: Double
        get() = getDouble("amount")
        set(value) = put("amount", value)
    var currency: Currency
        get() = Currency.valueOf(getString("currency"))
        set(value) = put("amount", value.toString())
    var date: Date
        get() = getDate("date")
        set(value) = put("date", value)
}
