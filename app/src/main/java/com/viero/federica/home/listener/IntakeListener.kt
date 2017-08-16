package com.viero.federica.home.listener

/**
 * This software has been developed by Ennova Research S.r.l.<br/>
 * <br/>
 * Project: federica<br/>
 * <br/>
 * created on: 2017-08-16
 *
 * @author Nicola De Fiorenze
 */
@FunctionalInterface
interface IntakeListener{
    fun updateQuantity(value: Int, foodKey: String)
}