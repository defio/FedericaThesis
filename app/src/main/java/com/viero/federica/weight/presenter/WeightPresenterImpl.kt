package com.viero.federica.weight.presenter

import com.github.defio.horizontalpicker.utils.isCurrentMonth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.viero.federica.application_commons.format
import com.viero.federica.database.Database
import com.viero.federica.database.DatabaseEntity
import com.viero.federica.settings.Settings
import com.viero.federica.weight.WeightContract
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat


/**
 * <br/>
 * Project: federica<br/>
 * <br/>
 * created on: 2017-09-10
 *
 * @author Nicola De Fiorenze
 */

class WeightPresenterImpl : WeightContract.WeightPresenter {

    private var view: WeightContract.WeightView? = null

    private var currentDate: DateTime = DateTime.now()

    private val eventListener: ValueEventListener = object : ValueEventListener {
        override fun onCancelled(databaseError: DatabaseError?) {
            println(databaseError)
        }

        override fun onDataChange(dataSnapshot: DataSnapshot?) {
            if (dataSnapshot?.value == null) {
                view?.showNoMeasurementsView()
                view?.hideMeasurementsView()
            } else {
                val map: Map<String, Double> = try {
                    dataSnapshot.value as Map<String, Double>
                } catch (e: Exception) {
                    view?.showNoMeasurementsView()
                    view?.hideMeasurementsView()

                    return
                }

                val convertedMap = map.mapKeys {
                    val formatter = DateTimeFormat.forPattern("dd-MM-yyyy")
                    formatter.parseDateTime(it.key) }
                        .filter { it.key.monthOfYear() == currentDate.monthOfYear() && it.key.year() == currentDate.year() }
                        .toList()
                        .sortedBy { (key, _) -> key }
                        .toMap()
                if (convertedMap.isEmpty()) {
                    view?.showNoMeasurementsView()
                    view?.hideMeasurementsView()

                } else {
                    view?.refreshRecyclerView(convertedMap)

                    view?.hideNoMeasurementsView()
                    view?.showMeasurementsView()
                }
            }
        }
    }

    override fun changeDate(dateSelected: DateTime) {
        currentDate = dateSelected
        fetchMeasurement()
    }


    override fun attachView(view: WeightContract.WeightView) {
        this.view = view
    }

    override fun deattachView() {
        view = null
        queryMeasurements().removeEventListener(eventListener)
    }

    override fun fetchMeasurement() {
        if (currentDate.isCurrentMonth()) {
            view?.showNewMeasurementButton()
        } else {
            view?.hideNewMeasurementButton()
        }

        queryMeasurements().removeEventListener(eventListener)
        queryMeasurements().addListenerForSingleValueEvent(eventListener)
    }

    override fun storeWeight(weight: Double) {
        Database.getChild(DatabaseEntity.WEIGHTS, Settings.getUserId()!!, currentDate.format()
        ).setValue(weight)

    }

    override fun refreshList() {
        fetchMeasurement()
    }

    private fun queryMeasurements(): Query =
            Database
                    .getChild(DatabaseEntity.WEIGHTS, Settings.getUserId()!!)

}