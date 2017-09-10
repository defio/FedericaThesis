package com.viero.federica.foods_commons.view

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.viero.federica.R
import com.viero.federica.aliments.view.AlimentsActivity
import com.viero.federica.aliments.view.AlimentsFragment
import com.viero.federica.foods_commons.FoodsContract
import com.viero.federica.foods_commons.FoodsContract.FoodsPresenter
import com.viero.federica.home.adapter.FoodsAdapter
import com.viero.federica.home.listener.IntakeListener
import com.viero.federica.home.model.FoodsWithIntakes
import com.viero.federica.home.view.HomeActivity
import com.viero.federica.home.view.HomeFragment
import kotlinx.android.synthetic.main.foods_fragment.view.*
import kotlinx.android.synthetic.main.top_buttons.view.*
import org.joda.time.DateTime

/**
 * Project: federica<br/>
 * <br/>
 * created on: 2017-08-26
 *
 * @author Nicola De Fiorenze
 */
abstract class FoodsFragment : Fragment(), FoodsContract.FoodsView {

    private val presenter: FoodsPresenter<FoodsContract.FoodsView> by lazy { getHomePresenter<FoodsContract.FoodsView>() }

    private val foodsAdapter = FoodsAdapter(object : IntakeListener {
        override fun increaseQuantity(value: Int, foodKey: String) {
            presenter.increaseQuantity(value, foodKey)
        }

        override fun decreaseQuantity(value: Int, foodKey: String) {
            presenter.decreaseQuantity(value, foodKey)

        }
    })

    abstract fun <T : FoodsContract.FoodsView> getHomePresenter(): FoodsPresenter<T>

    override fun onCreateView(inflater: android.view.LayoutInflater, container: android.view.ViewGroup?, savedInstanceState: android.os.Bundle?): android.view.View? {
        val rootView = inflater.inflate(R.layout.foods_fragment, container, false)
        presenter.attachView(this)

        initTopButtons(rootView)
        initDatePicker(rootView)

        val recyclerView = rootView.findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.adapter = foodsAdapter



        presenter.fetchFoods()
        return rootView
    }

    private fun initTopButtons(rootView: View) {
        rootView.home_button.setOnClickListener {
            if (this !is HomeFragment) {
                val intent = Intent(activity, HomeActivity::class.java)
                activity.startActivity(intent)
                activity.finish()
            }
        }

        rootView.aliments_button.setOnClickListener {
            if (this !is AlimentsFragment) {
                val intent = Intent(activity, AlimentsActivity::class.java)
                activity.startActivity(intent)
                activity.finish()
            }
        }

        rootView.findViewById(R.id.weight_button).setOnClickListener {
            //TODO
        }

        when (this) {
            is HomeFragment -> {
                rootView.home_button.apply {
                    setBackgroundColor(Color.parseColor("#FF4081"))
//                    isClickable = false
                }
            }
            is AlimentsFragment ->
                rootView.aliments_button.apply {
                    setBackgroundColor(Color.parseColor("#FF4081"))
//                    isClickable = false
                }
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

    override fun onDestroy() {
        super.onDestroy()
        presenter.deattachView()
    }

    override fun updateFoods(foods: FoodsWithIntakes) {
        foodsAdapter.setDataSet(foods)
        foodsAdapter.notifyDataSetChanged()
    }

    override fun showMultiselectDialog(items: Array<String>, selectedItems: BooleanArray?, onPositiveListener: (Map<Int, Boolean>) -> Unit) {
        val builder = AlertDialog.Builder(activity)
        val selected = mutableMapOf<Int, Boolean>()
        builder.setTitle(R.string.home_slotDialog_title)
                .setMultiChoiceItems(items, selectedItems) { _, which, isChecked -> selected.put(which, isChecked) }
                .setPositiveButton(R.string.generic_ok) { _, _ ->
                    onPositiveListener(selected)
                }
                .setNegativeButton(R.string.generic_cancel) { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
    }

}