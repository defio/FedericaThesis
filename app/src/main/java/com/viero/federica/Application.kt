package com.viero.federica

import android.app.Application
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
    }
}