package com.viero.federica

import android.app.Application
import com.google.firebase.crash.FirebaseCrash
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.viero.federica.database.Database
import com.viero.federica.database.DatabaseEntity
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
        Database.getChild(DatabaseEntity.SLOTS).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError?) {
                FirebaseCrash.log(databaseError?.toString())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                println(dataSnapshot?.value)
                @Suppress("UNCHECKED_CAST")
                val slotsKeys: List<String> = dataSnapshot?.let {
                    (it.value as java.util.HashMap<String, *>)
                }?.keys?.toList() ?: emptyList()
                Settings.setSlots(slotsKeys)
            }
        })
    }
}