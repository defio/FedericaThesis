package com.viero.federica.home.view

import android.support.v4.app.Fragment
import com.viero.federica.R
import com.viero.federica.database.model.Food
import com.viero.federica.home.HomeContract.HomePresenter
import com.viero.federica.home.HomeContract.HomeView
import com.viero.federica.home.presenter.HomePresenterImpl

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

    companion object {
        fun newInstance() = HomeFragment()
    }
    private val presenter: HomePresenter by lazy { getHomePresenter() }

    private fun getHomePresenter(): HomePresenter = HomePresenterImpl()

    override fun onCreateView(inflater: android.view.LayoutInflater, container: android.view.ViewGroup?, savedInstanceState: android.os.Bundle?): android.view.View? {
        val rootView = inflater.inflate(R.layout.home_fragment, container, false)
        presenter.attachView(this)


        presenter.fetchFoods()

        return rootView
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.deattachView()
    }

    override fun updateFoods(foods: MutableMap<String, Food>) {
        println("---------------------")
        println(foods)
    }
}
