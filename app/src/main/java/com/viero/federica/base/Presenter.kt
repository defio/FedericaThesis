package com.viero.federica.base

/**
 * This software has been developed by Ennova Research S.r.l.<br/>
 * <br/>
 * Project: federica<br/>
 * <br/>
 * created on: 2017-08-15
 *
 * @author Nicola De Fiorenze
 */

interface Presenter<in T: View> {
    fun attachView(view: T)
    fun deattachView()
}