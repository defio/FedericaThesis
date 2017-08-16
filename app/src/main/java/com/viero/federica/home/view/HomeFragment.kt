package com.viero.federica.home.view

import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.viero.federica.R
import com.viero.federica.database.model.Food
import com.viero.federica.home.HomeContract.HomePresenter
import com.viero.federica.home.HomeContract.HomeView
import com.viero.federica.home.adapter.FoodsAdapter
import com.viero.federica.home.listener.IntakeListener
import com.viero.federica.home.presenter.HomePresenterImpl
import kotlinx.android.synthetic.main.home_fragment.view.*
import org.joda.time.DateTime
import java.util.*

/**
 * This software has been developed by Ennova Research S.r.l.<br/>
 * <br/>
 * Project: federica<br/>
 * <br/>
 * created on: 2017-08-15
 *
 * @author Nicola De Fiorenze
 */
class HomeFragment : Fragment(), HomeView {

    val foodsAdapter = FoodsAdapter(object : IntakeListener {
        override fun updateQuantity(value: Int, foodKey: String) {
            presenter.updateQuantity(value,foodKey)
        }
    })

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val presenter: HomePresenter by lazy { getHomePresenter() }

    private fun getHomePresenter(): HomePresenter = HomePresenterImpl()

    override fun onCreateView(inflater: android.view.LayoutInflater, container: android.view.ViewGroup?, savedInstanceState: android.os.Bundle?): android.view.View? {
        val rootView = inflater.inflate(R.layout.home_fragment, container, false)
        presenter.attachView(this)


        val endDate = Calendar.getInstance()
        endDate.add(Calendar.MONTH, 1)

        val datePicker = rootView.datePicker
        datePicker
                .setListener { dateSelected -> presenter.changeDate(dateSelected) }
                .init()
        datePicker.setDate(DateTime.now())


        val recyclerView = rootView.findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.adapter = foodsAdapter

        presenter.fetchFoods()
        return rootView
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.deattachView()
    }

    override fun updateFoods(foods: MutableMap<String, Pair<Food, Int?>>) {
        foodsAdapter.setDataSet(foods)
        foodsAdapter.notifyDataSetChanged()
    }
}
