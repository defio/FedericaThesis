package com.viero.federica.aliments.view

import com.viero.federica.aliments.presenter.AlimentsPresenterImpl
import com.viero.federica.commons.Tracker
import com.viero.federica.foods_commons.FoodsContract
import com.viero.federica.foods_commons.view.FoodsFragment
import org.joda.time.DateTime

/**
 * Project: federica<br/>
 * <br/>
 * created on: 2017-08-15
 *
 * @author Nicola De Fiorenze
 */
class AlimentsFragment : FoodsFragment() {

    override fun <T : FoodsContract.FoodsView> getHomePresenter(): FoodsContract.FoodsPresenter<T> =
            AlimentsPresenterImpl()

    companion object {
        fun newInstance() = AlimentsFragment()
    }


    override fun trackFragmentLifeInterval(startTimestamp: DateTime, finishDateTime: DateTime) {
        Tracker.trackIntervalOperation(Tracker.IntervalOperation.ALIMENTS_LIFE_CYCLE, startTimestamp, finishDateTime)
    }
}