package com.viero.federica.base

/**
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