package com.viero.federica.application_commons

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

/**
 * Project: federica<br/>
 * <br/>
 * created on: 2017-08-17
 *
 * @author Nicola De Fiorenze
 */
fun DateTime.dateFormat() = DateTimeFormat.forPattern("dd-MM-yyyy").print(this)

fun DateTime.dateTimeFormat() = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm:ss").print(this)
