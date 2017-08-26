package com.viero.federica.application_commons

import android.app.Activity
import android.content.Intent
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity

/**
 * This software has been developed by Ennova Research S.r.l.<br/>
 * <br/>
 * Project: federica<br/>
 * <br/>
 * created on: 2017-08-15
 *
 * @author Nicola De Fiorenze
 */

fun AppCompatActivity.pushFragment(@LayoutRes containerViewId: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().replace(containerViewId, fragment).commit()
}

fun AppCompatActivity.hideActionBar() {
    supportActionBar?.hide()
}

fun AppCompatActivity.showActionBar() {
    supportActionBar?.show()
}


fun FragmentActivity.start(destinationClass: Class<out Activity>) {
    val intent = Intent(this, destinationClass)
    this.startActivity(intent)
}