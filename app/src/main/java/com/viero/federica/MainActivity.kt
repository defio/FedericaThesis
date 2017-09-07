package com.viero.federica

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.viero.federica.application_commons.start
import com.viero.federica.home.view.HomeActivity
import com.viero.federica.login.view.LoginActivity
import com.viero.federica.settings.Settings
import com.viero.federica.settings.SettingsKey

/**
 * Project: federica<br/>
 * <br/>
 * created on: 2017-08-15
 *
 * @author Nicola De Fiorenze
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Settings.getUserId().isNullOrEmpty()) {
            this.start(LoginActivity::class.java)
        } else {
            this.start(HomeActivity::class.java)
        }
        finish()
    }
}
