package com.viero.federica.home.presenter

import com.viero.federica.database.model.Food
import com.viero.federica.foods_commons.FoodsContract
import com.viero.federica.foods_commons.presenter.FoodsPresenterImpl


/**
 * Project: federica<br/>
 * <br/>
 * created on: 2017-08-15
 *
 * @author Nicola De Fiorenze
 */
class HomePresenterImpl<in T : FoodsContract.FoodsView> : FoodsPresenterImpl<T>() {

    override fun isFoodToFilterOut(food: Food): Boolean = !food.defaultInHome


}

