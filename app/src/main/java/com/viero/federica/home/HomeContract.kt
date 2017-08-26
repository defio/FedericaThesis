package com.viero.federica.home

import com.viero.federica.foods_commons.FoodsContract

/**
 * This software has been developed by Ennova Research S.r.l.<br/>
 * <br/>
 * Project: federica<br/>
 * <br/>
 * created on: 2017-08-15
 *
 * @author Nicola De Fiorenze
 */
interface HomeContract {
    interface HomeView : FoodsContract.FoodsView

    interface HomePresenter : FoodsContract.FoodsPresenter<HomeView>
}