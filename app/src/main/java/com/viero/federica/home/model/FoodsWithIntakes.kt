package com.viero.federica.home.model

import com.viero.federica.database.model.Food

/**
 * This software has been developed by Ennova Research S.r.l.<br/>
 * <br/>
 * Project: federica<br/>
 * <br/>
 * created on: 2017-08-17
 *
 * @author Nicola De Fiorenze
 */
class FoodsWithIntakes {
    val foodsMap = mutableMapOf<String, FoodIntakers>() //key == food.name

    fun addFood(food: Food) {
        foodsMap.put(food.name ?: "", FoodIntakers(food))
    }

    fun updateIntakeForFood(food: Food, intake: String,counter: Int){
        if(foodsMap[food.name]==null){
            addFood(food)
        }
        foodsMap[food.name]?.let {
            val foodWithIntake = it
            foodWithIntake.updateCounter(intake,counter)
        }
    }

    fun  remove(food: String) {
        foodsMap.remove(food)
    }

    fun getFoodAtPosition(position:Int): FoodIntakers {
        return foodsMap.values.toList()[position]
    }

    fun size():Int = foodsMap.size
}

class FoodIntakers(val food: Food) {
    val intakesCounter = IntakesCounter()

    fun updateCounter(intake: String, counter: Int) {
        intakesCounter.updateCounter(intake, counter)
    }
}

class IntakesCounter {
    val intakesCounter = mutableMapOf<String, Int>()

    fun getCounter(intake: String) = intakesCounter[intake] ?: 0

    fun updateCounter(intake: String, counter: Int) {
        intakesCounter.put(intake, counter)
    }

    fun sum() = intakesCounter.values.sum()
}