package com.viero.federica.foods_commons.view

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.viero.federica.R
import com.viero.federica.aliments.view.AlimentsActivity
import com.viero.federica.aliments.view.AlimentsFragment
import com.viero.federica.commons.TrackableFragment
import com.viero.federica.database.model.Food
import com.viero.federica.foods_commons.FoodsContract
import com.viero.federica.foods_commons.FoodsContract.FoodsPresenter
import com.viero.federica.home.adapter.FoodsAdapter
import com.viero.federica.home.listener.IntakeListener
import com.viero.federica.home.model.FoodsWithIntakes
import com.viero.federica.home.view.HomeActivity
import com.viero.federica.home.view.HomeFragment
import com.viero.federica.weight.view.WeightActivity
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
abstract class FoodsFragment : TrackableFragment(), FoodsContract.FoodsView {

    private val presenter: FoodsPresenter<FoodsContract.FoodsView> by lazy { getHomePresenter<FoodsContract.FoodsView>() }

    private val foodsAdapter = FoodsAdapter(object : IntakeListener {
        override fun increaseQuantity(value: Int, food: Food) {
            presenter.increaseQuantity(value, food)
        }

        override fun decreaseQuantity(value: Int, food: Food) {
            presenter.decreaseQuantity(value, food)
        }
    })

    abstract fun <T : FoodsContract.FoodsView> getHomePresenter(): FoodsPresenter<T>

    override fun onCreateView(inflater: android.view.LayoutInflater, container: android.view.ViewGroup?, savedInstanceState: android.os.Bundle?): android.view.View? {
        val rootView = inflater.inflate(R.layout.foods_fragment, container, false)
        presenter.attachView(this)

        initTopButtons(rootView)
        initDatePicker(rootView)

        val recyclerView = rootView.findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.layoutManager = GridLayoutManager(activity, 1, GridLayoutManager.VERTICAL, false)
        recyclerView.adapter = foodsAdapter

        presenter.fetchFoods()
        return rootView
    }

    private fun initTopButtons(rootView: View) {
        rootView.home_button.setOnClickListener {
            if (this !is HomeFragment) {
                start(HomeActivity::class.java)
            }
        }

        rootView.aliments_button.setOnClickListener {
            if (this !is AlimentsFragment) {
                start(AlimentsActivity::class.java)
            }
        }

        rootView.findViewById(R.id.weight_button).setOnClickListener {
            start(WeightActivity::class.java)
        }

        when (this) {
            is HomeFragment -> {
                rootView.home_button.apply {
                    setBackgroundResource(R.color.selectedTab)
                    setTextColor(Color.BLACK)
                }
            }
            is AlimentsFragment ->
                rootView.aliments_button.apply {
                    setBackgroundResource(R.color.selectedTab)
                    setTextColor(Color.BLACK)
                }
        }
    }

    private fun start(clazz: Class<out AppCompatActivity>) {
        val intent = Intent(activity, clazz)
        activity.startActivity(intent)
        activity.finish()
        activity.overridePendingTransition(0, 0)
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