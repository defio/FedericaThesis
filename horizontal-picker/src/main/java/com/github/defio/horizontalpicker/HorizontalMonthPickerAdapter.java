package com.github.defio.horizontalpicker;


import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.defio.horizontalpicker.model.Month;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import static com.github.defio.horizontalpicker.utils.DateTimeExtKt.isCurrentMonth;


/**
 * Created by ndefiorenze on 22/02/2017.
 */

public class HorizontalMonthPickerAdapter extends RecyclerView.Adapter<HorizontalMonthPickerAdapter.ViewHolder> {

    private final int mBackgroundColor;
    private final int mDateSelectedTextColor;
    private final int mDateSelectedColor;
    private final int mTodayDateTextColor;
    private final int mTodayDateBackgroundColor;
    private final int mUnselectedDayTextColor;
    private final OnItemClickedListener listener;
    private int itemWidth;
    private List<Month> items;

    public HorizontalMonthPickerAdapter(int itemWidth,
                                        OnItemClickedListener listener,
                                        int numberOfDaysToCreate,
                                        int offset,
                                        int mBackgroundColor,
                                        int mDateSelectedColor,
                                        int mDateSelectedTextColor,
                                        int mTodayDateTextColor,
                                        int mTodayDateBackgroundColor,
                                        int mUnselectedDayTextColor) {
        items = new ArrayList<>();
        this.itemWidth = itemWidth;
        this.listener = listener;
        generateDataSet(numberOfDaysToCreate,
                new DateTime()
                        .minusDays(offset)
                        .getMillis(),
                false);
        this.mBackgroundColor = mBackgroundColor;
        this.mDateSelectedTextColor = mDateSelectedTextColor;
        this.mDateSelectedColor = mDateSelectedColor;
        this.mTodayDateTextColor = mTodayDateTextColor;
        this.mTodayDateBackgroundColor = mTodayDateBackgroundColor;
        this.mUnselectedDayTextColor = mUnselectedDayTextColor;
    }

    private void generateDataSet(int n, long initialDate, boolean cleanArray) {
        if (cleanArray) {
            items.clear();
        }
        for (int i = 0; i < n; i++) {
            DateTime actualDate = new DateTime(initialDate).plusMonths(i);
            items.add(new Month(actualDate));
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_month, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Month item = getItem(position);
        holder.textViewMonth.setText(item.getMonth());
        if (item.isSelected()) {
            holder.textViewMonth.setBackground(getDaySelectedBackground(holder.itemView));
            holder.textViewMonth.setTextColor(mDateSelectedTextColor);
        } else if (isCurrentMonth(item.getDate())) {
            holder.textViewMonth.setBackground(getDayTodayBackground(holder.itemView));
            holder.textViewMonth.setTextColor(mTodayDateTextColor);
        } else {
            holder.textViewMonth.setBackgroundColor(mBackgroundColor);
            holder.textViewMonth.setTextColor(mUnselectedDayTextColor);
        }
    }

    private Drawable getDaySelectedBackground(View view) {
        Drawable drawable = view
                .getResources()
                .getDrawable(R.drawable.background_day_selected);
        DrawableCompat.setTint(drawable, mDateSelectedColor);
        return drawable;
    }

    private Drawable getDayTodayBackground(View view) {
        Drawable drawable = view
                .getResources()
                .getDrawable(R.drawable.background_day_today);
        if (mTodayDateBackgroundColor != -1) DrawableCompat.setTint(drawable, mTodayDateBackgroundColor);
        return drawable;
    }

    public Month getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewMonth;

        ViewHolder(View itemView) {
            super(itemView);
            textViewMonth = (TextView) itemView.findViewById(R.id.text_view_month);
            textViewMonth.setWidth(itemWidth);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClickView(v, getAdapterPosition());
        }
    }
}