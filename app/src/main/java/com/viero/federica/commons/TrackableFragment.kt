package com.viero.federica.commons

import android.support.v4.app.Fragment
import org.joda.time.DateTime

/**
 * Project: federica<br/>
 * <br/>
 * created on: 2017-10-01
 *
 * @author Nicola De Fiorenze
 */
abstract class TrackableFragment : Fragment() {

    private var startTimestamp = DateTime()

    override fun onStart() {
        super.onStart()
        startTimestamp = DateTime()
    }

    override fun onPause() {
        super.onPause()

        trackFragmentLifeInterval(startTimestamp,DateTime())
    }

    abstract fun trackFragmentLifeInterval(startTimestamp:DateTime, finishDateTime: DateTime)

}