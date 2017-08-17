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
    fun increaseQuantity(value: Int, foodKey: String)
    fun decreaseQuantity(value: Int, foodKey: String)
}