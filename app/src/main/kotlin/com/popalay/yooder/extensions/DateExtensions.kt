package com.popalay.yooder.extensions

fun Long.toRelatedDate() = android.text.format.DateUtils.getRelativeTimeSpanString(this,
        System.currentTimeMillis(), 0, android.text.format.DateUtils.FORMAT_ABBREV_ALL);