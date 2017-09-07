package com.viero.federica.login.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.viero.federica.R
import com.viero.federica.application_commons.hideActionBar
import com.viero.federica.application_commons.pushFragment

/**
 * Project: federica<br/>
 * <br/>
 * created on: 2017-08-15
 *
 * @author Nicola De Fiorenze
 */
class LoginActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hideActionBar()

        if (savedInstanceState == null) {
            pushFragment(R.id.container, LoginFragment.newInstance())
        }
    }

}