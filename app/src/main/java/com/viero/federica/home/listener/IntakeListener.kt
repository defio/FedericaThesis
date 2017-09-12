package com.viero.federica.home.listener

import com.viero.federica.database.model.Food

/**
 * Project: federica<br/>
 * <br/>
 * created on: 2017-08-16
 *
 * @author Nicola De Fiorenze
 */
@FunctionalInterface
interface IntakeListener{
    fun increaseQuantity(value: Int, food: Food)
    fun decreaseQuantity(value: Int, food: Food)
}