package com.viero.federica.weight.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.viero.federica.R
import kotlinx.android.synthetic.main.weight_row.view.*
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

/**
 * Project: federica<br/>
 * <br/>
 * created on: 2017-09-11
 *
 * @author Nicola De Fiorenze
 */
class WeightAdapter : RecyclerView.Adapter<WeightAdapter.WeightViewHolder>() {

    private var weights: Map<DateTime,Long>? = null

    override fun onBindViewHolder(holder: WeightViewHolder, position: Int) {
        weights?.toList()?.get(position)?.let {
            holder.dateTextView.text = DateTimeFormat.forPattern("dd-MM-yyyy").print(it.first)
            holder.weightTextView.text = it.second.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): WeightAdapter.WeightViewHolder {
        val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.weight_row, parent, false)
        return WeightViewHolder(view)
    }

    override fun getItemCount(): Int = weights?.size ?: 0


    fun setDataSet(weights: Map<DateTime,Long>) {
        this.weights = weights
    }

    class WeightViewHolder(container: View) : RecyclerView.ViewHolder(container) {
        val dateTextView: TextView = container.text_view_date
        val weightTextView: TextView = container.text_view_weight
    }
}