package com.viero.federica.home.presenter

import com.google.firebase.crash.FirebaseCrash
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.viero.federica.database.Database
import com.viero.federica.database.DatabaseEntity
import com.viero.federica.database.model.Food
import com.viero.federica.home.HomeContract
import com.viero.federica.home.HomeContract.HomePresenter
import com.viero.federica.home.model.FoodsWithIntakes
import com.viero.federica.settings.Settings
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat


/**
 * This software has been developed by Ennova Research S.r.l.<br/>
 * <br/>
 * Project: federica<br/>
 * <br/>
 * created on: 2017-08-15
 *
 * @author Nicola De Fiorenze
 */
class HomePresenterImpl : HomePresenter {

    var currentDate: DateTime = DateTime.now()

    var view: HomeContract.HomeView? = null
    var foods: FoodsWithIntakes = FoodsWithIntakes()

    val eventListener: ChildEventListener = object : ChildEventListener {
        override fun onCancelled(databaseError: DatabaseError?) {
            FirebaseCrash.log(databaseError?.toString())
        }

        override fun onChildMoved(dataSnapshot: DataSnapshot?, prevChildKey: String?) {
            updateFoodMap(prevChildKey, dataSnapshot)

            view?.updateFoods(foods)
        }

        override fun onChildChanged(dataSnapshot: DataSnapshot?, prevChildKey: String?) {
            updateFoodMap(prevChildKey, dataSnapshot)
        }

        private fun updateFoodMap(prevChildKey: String?, dataSnapshot: DataSnapshot?) {
            val oldKey = prevChildKey
            val newKey = dataSnapshot?.key
            val food = dataSnapshot?.getValue(Food::class.java) as Food
            if (oldKey != null) {
                foods.remove(prevChildKey)
            }
            if (newKey != null) {
                foods.remove(newKey)
            }
            if (newKey != null) {
                Database.getChild(DatabaseEntity.INTAKES, Settings.getUserId()!!, currentDate.format(),
                        newKey).addListenerForSingleValueEvent(
                        object : ValueEventListener {
                            override fun onCancelled(databaseError: DatabaseError?) {
                                FirebaseCrash.log(databaseError?.toString())
                            }

                            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                                println(dataSnapshot?.value)
                                @Suppress("UNCHECKED_CAST")
                                val value = dataSnapshot?.value as kotlin.collections.Map<String,Long?>?
                                foods.addFood(food)
                                value?.entries?.forEach { (intake, count) ->
                                    if(intake is String && count!=null && count is Long)
                                    foods.updateIntakeForFood(food, intake, count.toInt())
                                }
                                view?.updateFoods(foods)
                            }
                        })

            }
        }

        override fun onChildAdded(dataSnapshot: DataSnapshot?, prevChildKey: String?) {
            updateFoodMap(null, dataSnapshot)

            view?.updateFoods(foods)
        }

        override fun onChildRemoved(dataSnapshot: DataSnapshot?) {
            val key = dataSnapshot?.key
            if (key != null) {
                foods.remove(key)
            }

            view?.updateFoods(foods)
        }
    }


    override fun attachView(view: HomeContract.HomeView) {
        this.view = view
    }

    override fun deattachView() {
        view = null
        Database.getChild(DatabaseEntity.FOODS).removeEventListener(eventListener)
    }

    override fun fetchFoods() {
        Database.getChild(DatabaseEntity.FOODS).removeEventListener(eventListener)
        Database.getChild(DatabaseEntity.FOODS).addChildEventListener(eventListener)
    }

    override fun changeDate(dateSelected: DateTime) {
        currentDate = dateSelected
        fetchFoods()
    }

    override fun updateQuantity(value: Int, foodKey: String) {
        Database.getChild(DatabaseEntity.INTAKES, Settings.getUserId()!!, currentDate.format(),
                foodKey, "colazione").setValue(value)
    }
}

private fun DateTime.format() = DateTimeFormat.forPattern("dd-MM-yyyy").print(this)

