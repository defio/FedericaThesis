package com.viero.federica.home.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.viero.federica.R
import com.viero.federica.commons.hideActionBar
import com.viero.federica.commons.pushFragment

/**
 * This software has been developed by Ennova Research S.r.l.<br/>
 * <br/>
 * Project: federica<br/>
 * <br/>
 * created on: 2017-08-15
 *
 * @author Nicola De Fiorenze
 */
class HomeActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hideActionBar()

        if (savedInstanceState == null) {
            pushFragment(R.id.container, HomeFragment.newInstance())
        }
    }
}