package com.viero.federica.home.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
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
class HomeActivity : AppCompatActivity(){

    private var firebaseAnalytics: FirebaseAnalytics? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        hideActionBar()

        if (savedInstanceState == null) {
            pushFragment(R.id.container, HomeFragment.newInstance())
        }
    }
}