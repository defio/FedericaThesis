package com.viero.federica.settings

import android.content.Context
import android.content.SharedPreferences

/**
 * This software has been developed by Ennova Research S.r.l.<br/>
 * <br/>
 * Project: federica<br/>
 * <br/>
 * created on: 2017-08-15
 *
 * @author Nicola De Fiorenze
 */
object Settings {

    private val SHARED_PREFERENCES_NAME = "com.viero.federica.settings"
    private var sharedPreferences: SharedPreferences? = null

    fun init(context: Context): Settings {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        return this
    }

    fun set(key: SettingsKey, value: Any?) {
        sharedPreferences.let {
            val editor = it?.edit()
            when (value) {
                is String -> editor?.putString(key.name, value)
                else -> System.err.println("cannot save $value")
            }
            editor?.apply()
        }
    }

    fun getString(key: SettingsKey): String? = sharedPreferences?.getString(SettingsKey.USER_ID.name, null)

}

enum class SettingsKey {
    USER_ID
}