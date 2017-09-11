package com.viero.federica.weight

import com.viero.federica.base.Presenter
import com.viero.federica.base.View
import org.joda.time.DateTime

/**
 * <br/>
 * Project: federica<br/>
 * <br/>
 * created on: 2017-09-10
 *
 * @author Nicola De Fiorenze
 */

interface WeightContract{

    interface WeightView : View{
        fun showNoMeasurementsView()
        fun hideMeasurementsView()
        fun hideNoMeasurementsView()
        fun showMeasurementsView()
        fun showNewMeasurementButton()
        fun hideNewMeasurementButton()
        fun refreshRecyclerView(map: Map<DateTime, Long>)
    }

    interface WeightPresenter : Presenter<WeightView>{
        fun changeDate(dateSelected: DateTime)

        fun fetchMeasurement()
        fun storeWeight(weight: Int)
        fun refreshList()
    }
}