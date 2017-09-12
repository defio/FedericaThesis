package com.viero.federica.home.model

import com.viero.federica.database.model.Food

/**
 * Project: federica<br/>
 * <br/>
 * created on: 2017-08-17
 *
 * @author Nicola De Fiorenze
 */
class FoodsWithIntakes {
    private val foodsMap = mutableMapOf<String, FoodIntakers>() //key == food.key

    fun addFood(food: Food) {
        foodsMap.put(food.key ?: "", FoodIntakers(food))
    }

    fun updateIntakeForFood(food: Food, intake: String,counter: Int){
        if(foodsMap[food.key]==null){
            addFood(food)
        }
        foodsMap[food.key]?.updateCounter(intake,counter)
    }

    fun  remove(food: String) {
        foodsMap.remove(food)
    }

    fun getFoodAtPosition(position:Int): FoodIntakers = foodsMap.values.toList()[position]

    fun size():Int = foodsMap.size
}

class FoodIntakers(val food: Food) {
    val intakesCounter = IntakesCounter()

    fun updateCounter(intake: String, counter: Int) {
        intakesCounter.updateCounter(intake, counter)
    }
}

class IntakesCounter {
    private val intakesCounter = mutableMapOf<String, Int>()

    fun getCounter(intake: String) = intakesCounter[intake] ?: 0

    fun updateCounter(intake: String, counter: Int) {
        intakesCounter.put(intake, counter)
    }

    fun sum() = intakesCounter.values.sum()
}