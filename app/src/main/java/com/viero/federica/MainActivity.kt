package com.viero.federica

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.google.firebase.database.FirebaseDatabase
import com.viero.federica.login.view.LoginFragment

import com.viero.federica.commons.pushFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")

        myRef.setValue("Hello, World! ")

        if (savedInstanceState == null) {
            pushFragment(R.id.container, LoginFragment.newInstance())
        }
    }
}
