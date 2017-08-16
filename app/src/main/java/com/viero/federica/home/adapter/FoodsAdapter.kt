package com.viero.federica.home.adapter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.viero.federica.R
import com.viero.federica.database.model.Food
import com.viero.federica.home.listener.IntakeListener
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
class FoodsAdapter(val intakeListener: IntakeListener) : RecyclerView.Adapter<FoodsAdapter.FoodViewHolder>() {
    private var foods: MutableMap<String, Food> = mutableMapOf()

    override fun onBindViewHolder(holder: FoodsAdapter.FoodViewHolder, position: Int) {
        val foodEntry = foods.entries.toList()[position]

        holder.foodNameTextView.text = foodEntry.value.name
        holder.cardView.setCardBackgroundColor(Color.parseColor(foodEntry.value.color))
        holder.minusButton.setOnClickListener {
            val oldQuantity = holder.counterTextView.text.toString().toInt()
            var newQuantity = oldQuantity - 1
            if (newQuantity >= 0) {
                intakeListener.updateQuantity(newQuantity, foodEntry.key)
            } else {
                newQuantity = 0
            }
            holder.counterTextView.text = "$newQuantity"
        }
        holder.plusButton.setOnClickListener {
            val oldQuantity = holder.counterTextView.text.toString().toInt()
            val newQuantity = oldQuantity + 1
            holder.counterTextView.text = "$newQuantity"
            intakeListener.updateQuantity(newQuantity, foodEntry.key)
        }

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