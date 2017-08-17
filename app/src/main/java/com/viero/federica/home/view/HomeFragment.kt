package com.viero.federica.home.view

import android.app.AlertDialog
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.firebase.crash.FirebaseCrash
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.viero.federica.R
import com.viero.federica.database.Database
import com.viero.federica.database.DatabaseEntity
import com.viero.federica.home.HomeContract.HomePresenter
import com.viero.federica.home.HomeContract.HomeView
import com.viero.federica.home.adapter.FoodsAdapter
import com.viero.federica.home.listener.IntakeListener
import com.viero.federica.home.model.FoodsWithIntakes
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
        override fun increaseQuantity(value: Int, foodKey: String) {
            Database.getChild(DatabaseEntity.SLOTS).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError?) {
                    FirebaseCrash.log(databaseError?.toString())
                }

                override fun onDataChange(dataSnapshot: DataSnapshot?) {
                    println(dataSnapshot?.value)
                    val slots = dataSnapshot?.let {
                        (it.value as java.util.HashMap<*, *>).keys.toList().filterIsInstance<String>()
                    }?.toTypedArray() ?: emptyArray()
                    updateQuantity(value, foodKey, slots)
                }
            })
        }

        override fun decreaseQuantity(value: Int, foodKey: String) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        private fun updateQuantity(value: Int, foodKey: String, slots: Array<String>) {
            val builder = AlertDialog.Builder(activity)
            val selected = mutableMapOf<Int, Boolean>()
            builder.setTitle(R.string.home_slotDialog_title)
                    .setMultiChoiceItems(slots, null) { _, which, isChecked -> selected.put(which, isChecked) }
                    .setPositiveButton(R.string.generic_ok) { _, _ ->
                        println(selected)
                        presenter.updateQuantity(value, foodKey)
                    }
                    .setNegativeButton(R.string.generic_cancel) {
                        dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
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

    override fun updateFoods(foods: FoodsWithIntakes) {
        foodsAdapter.setDataSet(foods)
        foodsAdapter.notifyDataSetChanged()
    }
}
