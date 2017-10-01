package com.viero.federica.commons

import com.google.firebase.database.Exclude
import com.viero.federica.aliments.presenter.AlimentsPresenterImpl
import com.viero.federica.application_commons.dateFormat
import com.viero.federica.application_commons.dateTimeFormat
import com.viero.federica.database.Database
import com.viero.federica.database.DatabaseEntity
import com.viero.federica.home.presenter.HomePresenterImpl
import com.viero.federica.weight.presenter.WeightPresenterImpl
import org.joda.time.DateTime
import org.joda.time.Period
import java.util.*

/**
 * Project: federica<br/>
 * <br/>
 * created on: 2017-10-01
 *
 * @author Nicola De Fiorenze
 */
object Tracker {

    private lateinit var userId: String

    fun setUserId(userId: String) {
        this.userId = userId
    }

    private val operationMap = mutableMapOf<EventSources, TrackSession>()

    fun trackIntervalOperation(operation: IntervalOperation, initialDateTime: DateTime, finalDateTime: DateTime = DateTime()) {
        val eventSource = toEventSource(operation)
        if (operationMap.containsKey(eventSource)) {
            operationMap[eventSource]?.apply {
                begin = initialDateTime.dateTimeFormat()
                end = finalDateTime.dateTimeFormat()
                durationInSeconds = Period(initialDateTime,finalDateTime).seconds
            }
        } else {
            println("--> $initialDateTime, $finalDateTime")
            val trackSession = TrackSession()
            trackSession.begin = initialDateTime.dateTimeFormat()
            trackSession.end = finalDateTime.dateTimeFormat()
            trackSession.durationInSeconds = Period(initialDateTime,finalDateTime).seconds
            operationMap.put(eventSource, trackSession)
        }

        println(operationMap)

        Database.getChild(DatabaseEntity.USAGES, userId, DateTime().dateFormat(), eventSource.name).push().setValue(
                operationMap[eventSource])

        operationMap.clear()
    }


    fun trackAtomicOperation(operation: AtomicOperation, dateTime: DateTime = DateTime(), from: Class<*>, details: String = "") {
        val eventSource = toEventSource(from)
        val action = Action(operation, dateTime.dateTimeFormat(), details)

        if (operationMap.containsKey(eventSource)) {
            operationMap[eventSource]?.addAction(action)
        } else {
            val trackSession = TrackSession()
            trackSession.addAction(action)
            operationMap.put(eventSource, trackSession)
        }
    }

    private fun toEventSource(from: Class<*>) =
            when (from) {
                AlimentsPresenterImpl::class.java -> EventSources.ALIMENTS
                HomePresenterImpl::class.java -> EventSources.HOME
                WeightPresenterImpl::class.java -> EventSources.WEIGHT
                else -> EventSources.UNKNOWN
            }

    private fun toEventSource(from: IntervalOperation) =
            when (from) {
                IntervalOperation.ALIMENTS_LIFE_CYCLE -> EventSources.ALIMENTS
                IntervalOperation.HOME_LIFE_CYCLE -> EventSources.HOME
                IntervalOperation.WEIGHT_LIFE_CYCLE -> EventSources.WEIGHT
            }


    enum class EventSources {
        ALIMENTS,
        HOME,
        WEIGHT,
        UNKNOWN
    }


    enum class IntervalOperation {
        ALIMENTS_LIFE_CYCLE,
        HOME_LIFE_CYCLE,
        WEIGHT_LIFE_CYCLE
    }

    enum class AtomicOperation {
        INCREASE_QUANTITY,
        DECREASE_QUANTITY,
        UPDATE_QUANTITY,
        WEIGHT_INSERT
    }

    class TrackSession {
        private val id: String = UUID.randomUUID().toString()
            @Exclude get

        val actions = mutableListOf<Action>()

        var begin: String? = null
        var end: String? = null
        var durationInSeconds = -1

        fun addAction(action: Action) {
            actions.add(action)
        }

        override fun hashCode(): Int = id.hashCode()

        override fun toString(): String =
                """
                    start: $begin
                    finish: $end
                    durationInSeconds: $durationInSeconds
                    actions: $actions
                """.trimIndent()

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is TrackSession) return false

            if (id != other.id) return false

            return true
        }

    }

    data class Action(val operation: AtomicOperation,
                      val dateTime: String,
                      val detail: String)

}