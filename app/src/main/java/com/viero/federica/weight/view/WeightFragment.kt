package com.viero.federica.weight.view

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.view.View
import android.widget.EditText
import com.google.firebase.crash.FirebaseCrash
import com.viero.federica.R
import com.viero.federica.aliments.view.AlimentsActivity
import com.viero.federica.home.view.HomeActivity
import com.viero.federica.weight.WeightContract
import com.viero.federica.weight.adapter.WeightAdapter
import com.viero.federica.weight.presenter.WeightPresenterImpl
import kotlinx.android.synthetic.main.top_buttons.view.*
import kotlinx.android.synthetic.main.weight_fragment.*
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
    private val weightsAdapter by lazy {WeightAdapter()}

    override fun onCreateView(inflater: android.view.LayoutInflater, container: android.view.ViewGroup?, savedInstanceState: android.os.Bundle?): android.view.View? {
        val rootView = inflater.inflate(R.layout.weight_fragment, container, false)
        presenter.attachView(this)

        initTopButtons(rootView)
        initDatePicker(rootView)

        val recyclerView = rootView.findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.layoutManager = GridLayoutManager(activity, 1, GridLayoutManager.VERTICAL, false)
        recyclerView.adapter = weightsAdapter


        return rootView

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.fetchMeasurement()
    }

    override fun refreshRecyclerView(map: Map<DateTime, Long>) {
        weightsAdapter.setDataSet(map)
        weightsAdapter.notifyDataSetChanged()
    }

    override fun onStart() {
        super.onStart()
        insert_new_measurement_button.setOnClickListener { view ->
            val editText = EditText(view.context)
            editText.inputType = InputType.TYPE_CLASS_NUMBER
            AlertDialog.Builder(view.context)
                    .setMessage(R.string.fill_with_weight)
                    .setView(editText)
                    .setPositiveButton(getString(
                            R.string.weight_alert_insert), { dialog, whichButton ->
                        val weightString = editText.text.toString()
                        try {
                            presenter.storeWeight(weightString.toInt())
                            presenter.refreshList()
                        } catch (e: Exception) {
                            FirebaseCrash.log(e.toString())
                        }
                    })
                    .setNegativeButton(getString(
                            R.string.weight_alert_cancel), { dialog, whichButton ->
                        dialog.dismiss()
                    })
                    .show()
        }
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

    override fun showNoMeasurementsView() {
        no_measurement_layout.visibility = View.VISIBLE
    }

    override fun hideNoMeasurementsView() {
        no_measurement_layout.visibility = View.GONE
    }

    override fun hideMeasurementsView() {
        recycler_view.visibility = View.GONE
    }

    override fun showMeasurementsView() {
        recycler_view.visibility = View.VISIBLE
    }

    override fun showNewMeasurementButton() {
        insert_new_measurement_button.visibility = View.VISIBLE
    }

    override fun hideNewMeasurementButton() {
        insert_new_measurement_button.visibility = View.INVISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.deattachView()
    }

    companion object {
        fun newInstance() = WeightFragment()
    }


}


