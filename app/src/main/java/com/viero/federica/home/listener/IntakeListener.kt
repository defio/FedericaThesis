package com.viero.federica.home.listener

/**
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