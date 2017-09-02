package com.viero.federica

import android.app.Application
import com.google.firebase.crash.FirebaseCrash
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.viero.federica.database.Database
import com.viero.federica.database.DatabaseEntity
import com.viero.federica.database.model.Slot
import com.viero.federica.settings.Settings

/**
 * This software has been developed by Ennova Research S.r.l.<br/>
 * <br/>
 * Project: federica<br/>
 * <br/>
 * created on: 2017-08-15
 *
 * @author Nicola De Fiorenze
 */
class Application: Application(){

    lateinit var settings: Settings

    override fun onCreate() {
        super.onCreate()

        settings = Settings.init(this)

        fetchLots()
    }

    fun fetchLots(){
        Database.getChild(DatabaseEntity.SLOTS).orderByChild("order").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError?) {
                FirebaseCrash.log(databaseError?.toString())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                println(dataSnapshot?.value)

                val listOfSlots= mutableListOf<Slot>()
                dataSnapshot?.children?.forEach { child ->
                    listOfSlots.add(child?.getValue(Slot::class.java) as Slot)
                }

                val slotsKeys = mutableListOf<String>()
                listOfSlots.forEach { slotsKeys.add(it.name) }
                Settings.setSlots(slotsKeys)
            }
        })
    }
}