package com.viero.federica.database

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.security.InvalidParameterException

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

    init {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }

    private val firebase = FirebaseDatabase.getInstance().reference

    fun getChild(entity: DatabaseEntity): DatabaseReference = firebase.child(entity.schemaName)

    fun getChild(vararg entities: Any): DatabaseReference {
        val pathToChild = entities.map { entity ->
            when (entity) {
                is String -> entity
                is DatabaseEntity -> entity.schemaName
                else -> InvalidParameterException("entity must be a String or a DatabaseEntity")
            }
        }.joinToString(separator = "/")

        return firebase.child(pathToChild)
    }
}

enum class DatabaseEntity(val schemaName: String) {
    USERS("users"),
    INTAKES("intakes"),
    FOODS("foods")
}