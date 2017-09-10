package com.viero.federica.weight.presenter

import com.viero.federica.weight.WeightContract
import org.joda.time.DateTime

/**
 * <br/>
 * Project: federica<br/>
 * <br/>
 * created on: 2017-09-10
 *
 * @author Nicola De Fiorenze
 */

class WeightPresenterImpl : WeightContract.WeightPresenter {

    override fun changeDate(dateSelected: DateTime) {
        println("changeDate $dateSelected")
    }

    var view: WeightContract.WeightView? = null

    override fun attachView(view: WeightContract.WeightView) {
        this.view = view
    }

    override fun deattachView() {
        view = null
    }

}