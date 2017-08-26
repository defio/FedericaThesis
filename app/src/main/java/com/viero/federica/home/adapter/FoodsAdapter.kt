package com.viero.federica.home.adapter

import android.graphics.Color
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.viero.federica.R
import com.viero.federica.home.listener.IntakeListener
import com.viero.federica.home.model.FoodsWithIntakes
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
class FoodsAdapter(private val intakeListener: IntakeListener) : RecyclerView.Adapter<FoodsAdapter.FoodViewHolder>() {
    private var foodsWithIntakes: FoodsWithIntakes? = null

    override fun onBindViewHolder(holder: FoodsAdapter.FoodViewHolder, position: Int) {
        foodsWithIntakes?.let {
            val foodEntry = it.getFoodAtPosition(position)

            holder.foodNameTextView.text = foodEntry.food.name
            holder.counterTextView.text = foodEntry.intakesCounter.sum().toString()
            holder.cardView.setCardBackgroundColor(Color.parseColor(foodEntry.food.color))
            holder.minusButton.setOnClickListener {
                val oldQuantity = holder.counterTextView.text.toString().toInt()
                val newQuantity = oldQuantity - 1
                if (newQuantity >= 0) {
                    intakeListener.decreaseQuantity(newQuantity, foodEntry.food.name ?: "")
                }
            }
            holder.plusButton.setOnClickListener {
                val oldQuantity = holder.counterTextView.text.toString().toInt()
                val newQuantity = oldQuantity + 1
                intakeListener.increaseQuantity(newQuantity, foodEntry.food.name ?: "")
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FoodsAdapter.FoodViewHolder {
        val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.food_card, parent, false)
        return FoodViewHolder(view)
    }

    override fun getItemCount(): Int = foodsWithIntakes?.size() ?: 0


    fun setDataSet(foods: FoodsWithIntakes) {
        this.foodsWithIntakes = foods
    }

    class FoodViewHolder(container: View) : RecyclerView.ViewHolder(container) {
        val cardView: CardView = container.card_view
        val foodNameTextView: TextView = container.food_name_text_view
        val minusButton: Button = container.minus_button
        val plusButton: Button = container.plus_button
        val counterTextView: TextView = container.counter_text_view
    }
}