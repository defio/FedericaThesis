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

    }

    interface WeightPresenter : Presenter<WeightView>{
        fun changeDate(dateSelected: DateTime)

    }
}