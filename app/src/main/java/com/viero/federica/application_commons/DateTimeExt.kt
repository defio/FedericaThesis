package com.viero.federica.application_commons

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

/**
 * This software has been developed by Ennova Research S.r.l.<br/>
 * <br/>
 * Project: federica<br/>
 * <br/>
 * created on: 2017-08-17
 *
 * @author Nicola De Fiorenze
 */
fun DateTime.format() = DateTimeFormat.forPattern("dd-MM-yyyy").print(this)
