package com.github.defio.horizontalpicker.utils

import org.joda.time.DateTime

/**
 * This software has been developed by Ennova Research S.r.l.<br/>
 * <br/>
 * Project: HorizontalPicker<br/>
 * <p> This doc comment should contain a short class description.<p/>
 * <br/>
 * created on: 2017-09-09
 *
 * @author Nicola De Fiorenze
 */

fun DateTime.isToday(): Boolean {
    val today = DateTime().withTime(0, 0, 0, 0)
    return millis == today.millis
}

fun DateTime.isCurrentMonth(): Boolean {
    val today = DateTime().withTime(0, 0, 0, 0)
    return today
            .minusDays(today.dayOfMonth - 1)
            .isBefore(this) && isBefore(today
            .plusMonths(1)
            .minusDays(today.dayOfMonth - 1))
}