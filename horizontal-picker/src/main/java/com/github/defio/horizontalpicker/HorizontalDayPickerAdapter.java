package com.github.defio.horizontalpicker;


import android.app.AlarmManager;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.defio.horizontalpicker.model.Day;

import org.joda.time.DateTime;

import java.util.ArrayList;

import static com.github.defio.horizontalpicker.utils.DateTimeExtKt.isToday;


/**
 * Created by jhonn on 22/02/2017.
 */

public class HorizontalDayPickerAdapter extends RecyclerView.Adapter<HorizontalDayPickerAdapter.ViewHolder> {

    private static final long DAY_MILLIS = AlarmManager.INTERVAL_DAY;
    private final int mBackgroundColor;
    private final int mDateSelectedTextColor;
    private final int mDateSelectedColor;
    private final int mTodayDateTextColor;
    private final int mTodayDateBackgroundColor;
    private final int mDayOfWeekTextColor;
    private final int mUnselectedDayTextColor;
    private final OnItemClickedListener listener;
    private int itemWidth;
    private ArrayList<Day> items;

    public HorizontalDayPickerAdapter(int itemWidth,
                                      OnItemClickedListener listener,
                                      int daysToCreate,
                                      int offset,
                                      int mBackgroundColor,
                                      int mDateSelectedColor,
                                      int mDateSelectedTextColor,
                                      int mTodayDateTextColor,
                                      int mTodayDateBackgroundColor,
                                      int mDayOfWeekTextColor,
                                      int mUnselectedDayTextColor) {
        items = new ArrayList<>();
        this.itemWidth = itemWidth;
        this.listener = listener;
        generateDataSet(daysToCreate,
                new DateTime()
                        .minusDays(offset)
                        .getMillis(),
                false);
        this.mBackgroundColor = mBackgroundColor;
        this.mDateSelectedTextColor = mDateSelectedTextColor;
        this.mDateSelectedColor = mDateSelectedColor;
        this.mTodayDateTextColor = mTodayDateTextColor;
        this.mTodayDateBackgroundColor = mTodayDateBackgroundColor;
        this.mDayOfWeekTextColor = mDayOfWeekTextColor;
        this.mUnselectedDayTextColor = mUnselectedDayTextColor;
    }

    private void generateDataSet(int n, long initialDate, boolean cleanArray) {
        if (cleanArray) {
            items.clear();
        }
        for (int i = 0; i < n; i++) {
            DateTime actualDate = new DateTime(initialDate).plusDays(i);
            items.add(new Day(actualDate));
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_day, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Day item = getItem(position);
        holder.tvDay.setText(item.getDay());
        holder.tvWeekDay.setText(item.getWeekDay());
        holder.tvWeekDay.setTextColor(mDayOfWeekTextColor);
        if (item.isSelected()) {
            holder.tvDay.setBackground(getDaySelectedBackground(holder.itemView));
            holder.tvDay.setTextColor(mDateSelectedTextColor);
        } else if (isToday(item.getDate())) {
            holder.tvDay.setBackground(getDayTodayBackground(holder.itemView));
            holder.tvDay.setTextColor(mTodayDateTextColor);
        } else {
            holder.tvDay.setBackgroundColor(mBackgroundColor);
            holder.tvDay.setTextColor(mUnselectedDayTextColor);
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

    public Day getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvDay, tvWeekDay;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDay = (TextView) itemView.findViewById(R.id.tvDay);
            tvDay.setWidth(itemWidth);
            tvWeekDay = (TextView) itemView.findViewById(R.id.tvWeekDay);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClickView(v, getAdapterPosition());
        }
    }
}