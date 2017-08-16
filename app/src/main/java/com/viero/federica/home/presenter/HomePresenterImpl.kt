package com.viero.federica.home.presenter

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.viero.federica.database.Database
import com.viero.federica.database.DatabaseEntity
import com.viero.federica.database.model.Food
import com.viero.federica.home.HomeContract
import com.viero.federica.home.HomeContract.HomePresenter
import com.viero.federica.settings.Settings
import java.text.SimpleDateFormat
import java.util.*

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

    var view: HomeContract.HomeView? = null
    var foods: MutableMap<String, Pair<Food, Int?>> = mutableMapOf()

    val eventListener: ChildEventListener = object : ChildEventListener {
        override fun onCancelled(databaseError: DatabaseError?) {
            System.err.println(databaseError?.toString())
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
                Database.getChild(DatabaseEntity.INTAKES, Settings.getUserId()!!, Date().today(),
                        newKey).addListenerForSingleValueEvent(
                        object : ValueEventListener {
                            override fun onCancelled(databaseError: DatabaseError?) {
                                System.err.println(databaseError?.toString())
                            }

                            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                                val value = dataSnapshot?.getValue(Int::class.java)
                                val key = dataSnapshot?.key
                                foods.put(newKey, food to value)
                                view?.updateFoods(foods)
                                println("$key -> $value")
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
        Database.getChild(DatabaseEntity.FOODS).addChildEventListener(eventListener)

    }
}

private fun Date.today() = SimpleDateFormat("dd-MM-yyyy", Locale.ITALIAN).format(this)

