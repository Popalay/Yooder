package com.popalay.yooder.extensions

fun Long.toRelatedDate(): CharSequence {
    if (System.currentTimeMillis() - this < 2000 * 60) {
        return "just now"
    }
    return android.text.format.DateUtils.getRelativeTimeSpanString(this,
            System.currentTimeMillis(), 0, android.text.format.DateUtils.FORMAT_ABBREV_ALL);
}