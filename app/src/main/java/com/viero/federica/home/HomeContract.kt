package com.viero.federica.home

import com.viero.federica.base.Presenter
import com.viero.federica.base.View
import com.viero.federica.database.model.Food

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
        fun updateFoods(foods: MutableMap<String, Food>)
    }

    interface HomePresenter : Presenter<HomeView> {
        fun fetchFoods()
    }
}