package com.viero.federica.aliments

import com.viero.federica.foods_commons.FoodsContract

/**
 * This software has been developed by Ennova Research S.r.l.<br/>
 * <br/>
 * Project: federica<br/>
 * <br/>
 * created on: 2017-08-26
 *
 * @author Nicola De Fiorenze
 */
interface AlimentsContract {
    interface AlimentsView : FoodsContract.FoodsView

    interface AlimentsPresenter : FoodsContract.FoodsPresenter<AlimentsView>
}