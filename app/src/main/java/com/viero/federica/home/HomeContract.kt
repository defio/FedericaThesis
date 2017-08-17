package com.viero.federica.home

import com.viero.federica.base.Presenter
import com.viero.federica.base.View
import com.viero.federica.home.model.FoodsWithIntakes
import org.joda.time.DateTime

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
    interface HomeView : View {
        fun updateFoods(foods: FoodsWithIntakes)
    }

    interface HomePresenter : Presenter<HomeView> {
        fun fetchFoods()
        fun changeDate(dateSelected: DateTime)
        fun  updateQuantity(value: Int, foodKey: String)
    }
}