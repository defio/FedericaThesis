package com.viero.federica.database

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 * This software has been developed by Ennova Research S.r.l.<br/>
 * <br/>
 * Project: federica<br/>
 * <br/>
 * created on: 2017-08-15
 *
 * @author Nicola De Fiorenze
 */
object Database {

    private val firebase = FirebaseDatabase.getInstance().reference

    fun getChild(entity: DatabaseEntity): DatabaseReference{
        return when (entity) {
            DatabaseEntity.USERS -> firebase.child("users")
            DatabaseEntity.FOODS -> firebase.child("foods")
        }
    }



}

enum class DatabaseEntity {
    USERS,
    FOODS
}