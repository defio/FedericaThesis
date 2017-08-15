package com.viero.federica.login.presenter

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.viero.federica.base.View
import com.viero.federica.database.Database
import com.viero.federica.database.DatabaseEntity
import com.viero.federica.login.LoginContract
import com.viero.federica.login.LoginContract.LoginPresenter
import com.viero.federica.login.LoginContract.LoginView
import com.viero.federica.login.model.User
import com.viero.federica.settings.Settings
import com.viero.federica.settings.SettingsKey
import java.security.InvalidParameterException
import java.util.*

/**
 * This software has been developed by Ennova Research S.r.l.<br/>
 * <br/>
 * Project: federica<br/>
 * <br/>
 * created on: 2017-08-15
 *
 * @author Nicola De Fiorenze
 */
class LoginPresenterImpl : LoginPresenter {


    var view: LoginView? = null
    var userName: String? = null
    var userSurname: String? = null

    override fun newName(string: String) {
        userName = string
        checkOkButtonStatus()
    }

    override fun newSurname(string: String) {
        userSurname = string
        checkOkButtonStatus()
    }

    private fun checkOkButtonStatus() {
        view?.setOkButtonEnabled(userName.isNullOrEmpty().not() && userSurname.isNullOrEmpty().not())
    }

    override fun login() {
        println(User(userName!!, userSurname!!))

        val userUUID = UUID.randomUUID().toString()

        Settings.set(SettingsKey.USER_ID, userUUID)
        Database.getChild(DatabaseEntity.USERS)
                .child(userUUID)
                .setValue(User(userName!!, userSurname!!))
        view?.loginDone()
    }


    override fun attachView(view: LoginView) {
        this.view = view
        checkOkButtonStatus()
    }

    override fun deattachView() {
        this.view = null
    }
}