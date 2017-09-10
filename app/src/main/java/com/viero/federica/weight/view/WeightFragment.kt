package com.viero.federica.weight.view

import android.content.Intent
import android.graphics.Color
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.viero.federica.R
import com.viero.federica.aliments.view.AlimentsActivity
import com.viero.federica.home.view.HomeActivity
import com.viero.federica.weight.WeightContract
import com.viero.federica.weight.presenter.WeightPresenterImpl
import kotlinx.android.synthetic.main.top_buttons.view.*
import kotlinx.android.synthetic.main.weight_fragment.view.*
import org.joda.time.DateTime

/**
 * Project: federica<br/>
 * <br/>
 * created on: 2017-09-10
 *
 * @author Nicola De Fiorenze
 */
class WeightFragment : Fragment(), WeightContract.WeightView {

    private val presenter: WeightContract.WeightPresenter by lazy { WeightPresenterImpl() }

    override fun onCreateView(inflater: android.view.LayoutInflater, container: android.view.ViewGroup?, savedInstanceState: android.os.Bundle?): android.view.View? {
        val rootView = inflater.inflate(R.layout.weight_fragment, container, false)
        presenter.attachView(this)

        initTopButtons(rootView)
        initDatePicker(rootView)

        val recyclerView = rootView.findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
//        recyclerView.adapter = foodsAdapter


//        presenter.fetchFoods()
        return rootView
    }

    private fun initTopButtons(rootView: View) {
        rootView.home_button.setOnClickListener {
            val intent = Intent(activity, HomeActivity::class.java)
            activity.startActivity(intent)
            activity.finish()
        }

        rootView.aliments_button.setOnClickListener {
            val intent = Intent(activity, AlimentsActivity::class.java)
            activity.startActivity(intent)
            activity.finish()
        }

        rootView.findViewById(R.id.weight_button).apply {
            setBackgroundColor(Color.parseColor("#FF4081"))
            isClickable = false
        }

    }

    private fun initDatePicker(rootView: View) {
        val datePicker = rootView.datePicker
        datePicker
                .showTodayButton(false)
                .setListener { dateSelected -> presenter.changeDate(dateSelected) }
                .init()
        datePicker.setDate(DateTime.now())
    }


    companion object {
        fun newInstance() = WeightFragment()
    }


}


