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
        fun showMultiselectDialog(items: Array<String>, selectedItems: BooleanArray?, onPositiveListener: (Map<Int, Boolean>)->Unit)
    }

    interface HomePresenter : Presenter<HomeView> {
        fun fetchFoods()
        fun changeDate(dateSelected: DateTime)
        fun updateQuantity(foodKey: String, slots: Map<String,Long>, onDone: () -> Unit)
        fun increaseQuantity(value: Int, foodKey: String)
        fun decreaseQuantity(value: Int, foodKey: String)
    }
}