package com.viero.federica.foods_commons

import com.viero.federica.base.Presenter
import com.viero.federica.base.View
import com.viero.federica.database.model.Food
import com.viero.federica.home.model.FoodsWithIntakes
import org.joda.time.DateTime

/**
 * Project: federica<br/>
 * <br/>
 * created on: 2017-08-26
 *
 * @author Nicola De Fiorenze
 */
interface FoodsContract {
    interface FoodsView : View {
        fun updateFoods(foods: FoodsWithIntakes)
        fun showMultiselectDialog(items: Array<String>, selectedItems: BooleanArray?, onPositiveListener: (Map<Int, Boolean>)->Unit)
    }

    interface FoodsPresenter<in T: FoodsView> : Presenter<T> {
        fun fetchFoods()
        fun changeDate(dateSelected: DateTime)
        fun updateQuantity(foodKey: String, slots: Map<String,Long>, onDone: () -> Unit)
        fun increaseQuantity(value: Int, food: Food)
        fun decreaseQuantity(value: Int, food: Food)
    }
}