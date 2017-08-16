package com.viero.federica.home.adapter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.viero.federica.R
import com.viero.federica.database.model.Food
import kotlinx.android.synthetic.main.food_card.view.*

/**
 * This software has been developed by Ennova Research S.r.l.<br/>
 * <br/>
 * Project: federica<br/>
 * <p> This doc comment should contain a short class description.<p/>
 * <br/>
 * created on: 2017-08-15
 *
 * @author Nicola De Fiorenze
 */
class FoodsAdapter : RecyclerView.Adapter<FoodsAdapter.FoodViewHolder>() {
    private var foods: MutableMap<String, Food> = mutableMapOf()

    override fun onBindViewHolder(holder: FoodsAdapter.FoodViewHolder, position: Int) {
        val food = foods.values.toList()[position]

        holder.foodNameTextView.text = food.name
        holder.cardView.setCardBackgroundColor(Color.parseColor(food.color))

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FoodsAdapter.FoodViewHolder {
        val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.food_card, parent, false)
        return FoodViewHolder(view)
    }

    override fun getItemCount(): Int = foods.size


    fun setDataSet(foods: MutableMap<String, Food>) {
        this.foods = foods
    }

    class FoodViewHolder(container: View) : RecyclerView.ViewHolder(container) {

        val cardView = container.card_view
        val foodNameTextView = container.food_name_text_view
        val minusButton = container.minus_button
        val plusButton = container.plus_button
        val counterTextView = container.counter_text_view
    }
}