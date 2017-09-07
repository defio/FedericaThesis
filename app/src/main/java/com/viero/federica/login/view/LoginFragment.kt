package com.viero.federica.login.view

import android.support.v4.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.viero.federica.R
import com.viero.federica.application_commons.start
import com.viero.federica.home.view.HomeActivity
import com.viero.federica.login.LoginContract.LoginView
import com.viero.federica.login.presenter.LoginPresenterImpl

/**
 * Project: federica<br/>
 * <br/>
 * created on: 2017-08-15
 *
 * @author Nicola De Fiorenze
 */
class LoginFragment : Fragment(), LoginView {

    var okButton: Button? = null

    companion object {
        fun newInstance() = com.viero.federica.login.view.LoginFragment()
    }

    private val presenter: com.viero.federica.login.LoginContract.LoginPresenter by lazy { getLoginPresenter() }

    fun getLoginPresenter(): com.viero.federica.login.LoginContract.LoginPresenter = LoginPresenterImpl()

    override fun onCreateView(inflater: android.view.LayoutInflater, container: android.view.ViewGroup?, savedInstanceState: android.os.Bundle?): android.view.View? {
        val rootView = inflater.inflate(R.layout.login_fragment, container, false)
        presenter.attachView(this)

        initOkButton(rootView)
        initNameTextView(rootView)
        initSurnameTextView(rootView)

        return rootView
    }

    private fun initSurnameTextView(rootView: View) {
        RxTextView.textChanges(rootView.findViewById(R.id.editText_surname) as TextView)
                .filter { it != null }
                .map { charSequence -> charSequence.toString() }
                .subscribe { string ->
                    presenter.newSurname(string)
                }
    }

    private fun initNameTextView(rootView: View) {
        RxTextView.textChanges(
                rootView.findViewById(R.id.editText_name) as TextView)
                .filter { it != null }
                .map { charSequence -> charSequence.toString() }
                .subscribe { string ->
                    presenter.newName(string)
                }
    }

    private fun initOkButton(rootView: View) {
        okButton = rootView.findViewById(R.id.button_login) as Button
        RxView.clicks(okButton!!)
                .subscribe { presenter.login() }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.deattachView()
    }

    override fun setOkButtonEnabled(isEnabled: Boolean) {
        okButton?.isEnabled = isEnabled
    }

    override fun loginDone() {
        activity.finish()
        activity.start(HomeActivity::class.java)
    }

}
