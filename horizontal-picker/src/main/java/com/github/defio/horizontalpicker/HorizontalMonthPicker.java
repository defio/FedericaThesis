package com.github.defio.horizontalpicker;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.util.Locale;

import static com.github.defio.horizontalpicker.utils.DateTimeExtKt.isCurrentMonth;


/**
 * Created by Jhonny Barrios on 22/02/2017.
 */
public class HorizontalMonthPicker extends LinearLayout implements HorizontalPickerListener {

    private static final int NO_SETTED = -1;
    private View vHover;
    private TextView textViewYear;
    private TextView tvToday;
    private DatePickerListener listener;
    private HorizontalPickerMonthRecyclerView recyclerViewMonth;
    private int days;
    private int offset;
    private int mDateSelectedColor = -1;
    private int mDateSelectedTextColor = -1;
    private int mMonthAndYearTextColor = -1;
    private int mTodayButtonTextColor = -1;
    private boolean showTodayButton = true;
    private int mTodayDateTextColor = -1;
    private int mTodayDateBackgroundColor = -1;
    private int mDayOfWeekTextColor = -1;
    private int mUnselectedDayTextColor = -1;

    public HorizontalMonthPicker(Context context) {
        super(context);
        internInit();
    }

    public HorizontalMonthPicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        internInit();

    }

    public HorizontalMonthPicker(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        internInit();
    }

    private void internInit() {
        this.days = NO_SETTED;
        this.offset = NO_SETTED;
    }

    public HorizontalMonthPicker setListener(DatePickerListener listener) {
        this.listener = listener;
        return this;
    }

    public void setDate(final DateTime date) {
        recyclerViewMonth.post(new Runnable() {
            @Override
            public void run() {
                recyclerViewMonth.setDate(date);
            }
        });
    }

    public void init() {
        inflate(getContext(), R.layout.horizontal_month_picker, this);
        recyclerViewMonth = (HorizontalPickerMonthRecyclerView) findViewById(R.id.recyclerView);
        int DEFAULT_DAYS_TO_PLUS = 120;
        int finalDays = days == NO_SETTED ? DEFAULT_DAYS_TO_PLUS : days;
        int DEFAULT_INITIAL_OFFSET = 7;
        int finalOffset = offset == NO_SETTED ? DEFAULT_INITIAL_OFFSET : offset;
        vHover = findViewById(R.id.vHover);
        textViewYear = (TextView) findViewById(R.id.text_view_year);
        tvToday = (TextView) findViewById(R.id.tvToday);
        recyclerViewMonth.setListener(this);
        tvToday.setOnClickListener(recyclerViewMonth);
        textViewYear.setTextColor(mMonthAndYearTextColor != -1 ? mMonthAndYearTextColor : getColor(R.color.primaryTextColor));
        tvToday.setVisibility(showTodayButton ? VISIBLE : INVISIBLE);
        tvToday.setTextColor(mTodayButtonTextColor != -1 ? mTodayButtonTextColor : getColor(R.color.colorPrimary));
        int mBackgroundColor = getBackgroundColor();
        setBackgroundColor(mBackgroundColor != Color.TRANSPARENT ? mBackgroundColor : Color.WHITE);
        mDateSelectedColor = mDateSelectedColor == -1 ? getColor(R.color.colorPrimary) : mDateSelectedColor;
        mDateSelectedTextColor = mDateSelectedTextColor == -1 ? Color.WHITE : mDateSelectedTextColor;
        mTodayDateTextColor = mTodayDateTextColor == -1 ? getColor(R.color.primaryTextColor) : mTodayDateTextColor;
        mDayOfWeekTextColor = mDayOfWeekTextColor == -1 ? getColor(R.color.secundaryTextColor) : mDayOfWeekTextColor;
        mUnselectedDayTextColor = mUnselectedDayTextColor == -1 ? getColor(R.color.primaryTextColor) : mUnselectedDayTextColor;
        recyclerViewMonth.init(getContext(),
                finalDays,
                finalOffset,
                mBackgroundColor,
                mDateSelectedColor,
                mDateSelectedTextColor,
                mTodayDateTextColor,
                mTodayDateBackgroundColor,
                mUnselectedDayTextColor);
    }

    private int getColor(int colorId) {
        return getResources().getColor(colorId);
    }

    public int getBackgroundColor() {
        int color = Color.TRANSPARENT;
        Drawable background = getBackground();
        if (background instanceof ColorDrawable) color = ((ColorDrawable) background).getColor();
        return color;
    }

    @Override
    public boolean post(Runnable action) {
        return recyclerViewMonth.post(action);
    }

    @Override
    public void onStopDraggingPicker() {
        if (vHover.getVisibility() == VISIBLE) vHover.setVisibility(INVISIBLE);
    }

    @Override
    public void onDraggingPicker() {
        if (vHover.getVisibility() == INVISIBLE) vHover.setVisibility(VISIBLE);
    }

    @Override
    public void onDateSelected(DateTime item) {
        textViewYear.setText(item.toString("YYYY", Locale.getDefault()));
        if (showTodayButton) tvToday.setVisibility(isCurrentMonth(item) ? INVISIBLE : VISIBLE);
        if (listener != null) {
            listener.onDateSelected(item);
        }
    }



    public int getDays() {
        return days;
    }

    public HorizontalMonthPicker setDays(int days) {
        this.days = days;
        return this;
    }

    public int getOffset() {
        return offset;
    }

    public HorizontalMonthPicker setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    public HorizontalMonthPicker setDateSelectedColor(@ColorInt int color) {
        mDateSelectedColor = color;
        return this;
    }

    public HorizontalMonthPicker setDateSelectedTextColor(@ColorInt int color) {
        mDateSelectedTextColor = color;
        return this;
    }

    public HorizontalMonthPicker setMonthAndYearTextColor(@ColorInt int color) {
        mMonthAndYearTextColor = color;
        return this;
    }

    public HorizontalMonthPicker setTodayButtonTextColor(@ColorInt int color) {
        mTodayButtonTextColor = color;
        return this;
    }

    public HorizontalMonthPicker showTodayButton(boolean show) {
        showTodayButton = show;
        return this;
    }

    public HorizontalMonthPicker setTodayDateTextColor(int color) {
        mTodayDateTextColor = color;
        return this;
    }

    public HorizontalMonthPicker setTodayDateBackgroundColor(@ColorInt int color) {
        mTodayDateBackgroundColor = color;
        return this;
    }

    public HorizontalMonthPicker setDayOfWeekTextColor(@ColorInt int color) {
        mDayOfWeekTextColor = color;
        return this;
    }

    public HorizontalMonthPicker setUnselectedDayTextColor(@ColorInt int color) {
        mUnselectedDayTextColor = color;
        return this;
    }
}