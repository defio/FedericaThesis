package com.viero.federica.login

import com.viero.federica.base.Presenter
import com.viero.federica.base.View

/**
 * This software has been developed by Ennova Research S.r.l.<br/>
 * <br/>
 * Project: federica<br/>
 * <br/>
 * created on: 2017-08-15
 *
 * @author Nicola De Fiorenze
 */
interface LoginContract {
    interface LoginView : View {
        fun setOkButtonEnabled(isEnabled: Boolean)
        fun loginDone()
    }

    interface LoginPresenter : Presenter<LoginView> {
        fun newName(string: String)
        fun newSurname(string: String)
        fun login()
    }
}