package com.viero.federica.home.view

import com.viero.federica.foods_commons.FoodsContract
import com.viero.federica.foods_commons.view.FoodsFragment
import com.viero.federica.home.presenter.HomePresenterImpl


/**
 * Project: federica<br/>
 * <br/>
 * created on: 2017-08-15
 *
 * @author Nicola De Fiorenze
 */
class HomeFragment : FoodsFragment() {

    override fun <T : FoodsContract.FoodsView> getHomePresenter(): FoodsContract.FoodsPresenter<T> =
            HomePresenterImpl()


    companion object {
        fun newInstance() = HomeFragment()
    }





}
