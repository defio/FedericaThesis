package com.viero.federica.commons

import com.viero.federica.aliments.presenter.AlimentsPresenterImpl
import com.viero.federica.home.presenter.HomePresenterImpl
import com.viero.federica.weight.presenter.WeightPresenterImpl
import org.joda.time.DateTime
import org.joda.time.Seconds

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
                start = initialDateTime
                finish = finalDateTime
            }
        } else {
            val trackSession = TrackSession()
            trackSession.start = initialDateTime
            trackSession.finish = finalDateTime
            operationMap.put(eventSource, trackSession)
        }

        println(operationMap)
        operationMap.clear()
    }


    fun trackAtomicOperation(operation: AtomicOperation, dateTime: DateTime = DateTime(), from: Class<*>, details: String = "") {
        val eventSource = toEventSource(from)
        val action = Action(operation, dateTime, details)

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
        UPDATE_QUANTITY
    }

    class TrackSession {
        var start: DateTime = DateTime()
        var durationInSeconds = -1
        val actions = mutableListOf<Action>()

        var finish: DateTime = DateTime()
            set(value) {
                durationInSeconds = Seconds.secondsBetween(start, value).seconds
            }

        fun addAction(action: Action) {
            actions.add(action)
        }

        override fun hashCode(): Int = start.hashCode()

        override fun toString(): String =
                """
                    start: $start
                    finish: $finish
                    durationInSeconds: $durationInSeconds
                    actions: $actions
                """.trimIndent()

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is TrackSession) return false

            if (start != other.start) return false
            if (durationInSeconds != other.durationInSeconds) return false
            if (actions != other.actions) return false
            if (finish != other.finish) return false

            return true
        }

    }

    data class Action(val operation: AtomicOperation,
                      val dateTime: DateTime,
                      val detail: String)

}