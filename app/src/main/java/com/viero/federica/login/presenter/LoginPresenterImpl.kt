package com.viero.federica.login.presenter

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.viero.federica.base.View
import com.viero.federica.login.LoginContract
import com.viero.federica.login.LoginContract.LoginPresenter
import com.viero.federica.login.LoginContract.LoginView
import com.viero.federica.login.model.User
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

        val database = FirebaseDatabase.getInstance().reference
        database.child("users").child(UUID.randomUUID().toString()).setValue(User(userName!!, userSurname!!))
    }

    override fun attachView(view: View) {
        if (view is LoginView) {
            this.view = view
            checkOkButtonStatus()
        } else {
            throw InvalidParameterException("Invalid View for LoginPresenter")
        }
    }

    override fun deattachView() {
        this.view = null
    }
}