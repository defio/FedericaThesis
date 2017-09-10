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

import static com.github.defio.horizontalpicker.utils.DateTimeExtKt.isToday;

/**
 * Created by Jhonny Barrios on 22/02/2017.
 */

public class HorizontalDayPicker extends LinearLayout implements HorizontalPickerListener {

    private static final int NO_SETTED = -1;
    private View vHover;
    private TextView tvMonth;
    private TextView tvToday;
    private DatePickerListener listener;
    private HorizontalPickerDayRecyclerView rvDays;
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

    public HorizontalDayPicker(Context context) {
        super(context);
        internInit();
    }

    public HorizontalDayPicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        internInit();

    }

    public HorizontalDayPicker(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        internInit();
    }

    private void internInit() {
        this.days = NO_SETTED;
        this.offset = NO_SETTED;
    }

    public HorizontalDayPicker setListener(DatePickerListener listener) {
        this.listener = listener;
        return this;
    }

    public void setDate(final DateTime date) {
        rvDays.post(new Runnable() {
            @Override
            public void run() {
                rvDays.setDate(date);
            }
        });
    }

    public void init() {
        inflate(getContext(), R.layout.horizontal_picker, this);
        rvDays = (HorizontalPickerDayRecyclerView) findViewById(R.id.recyclerView);
        int DEFAULT_DAYS_TO_PLUS = 120;
        int finalDays = days == NO_SETTED ? DEFAULT_DAYS_TO_PLUS : days;
        int DEFAULT_INITIAL_OFFSET = 7;
        int finalOffset = offset == NO_SETTED ? DEFAULT_INITIAL_OFFSET : offset;
        vHover = findViewById(R.id.vHover);
        tvMonth = (TextView) findViewById(R.id.text_view_year);
        tvToday = (TextView) findViewById(R.id.tvToday);
        rvDays.setListener(this);
        tvToday.setOnClickListener(rvDays);
        tvMonth.setTextColor(mMonthAndYearTextColor != -1 ? mMonthAndYearTextColor : getColor(R.color.primaryTextColor));
        tvToday.setVisibility(showTodayButton ? VISIBLE : INVISIBLE);
        tvToday.setTextColor(mTodayButtonTextColor != -1 ? mTodayButtonTextColor : getColor(R.color.colorPrimary));
        int mBackgroundColor = getBackgroundColor();
        setBackgroundColor(mBackgroundColor != Color.TRANSPARENT ? mBackgroundColor : Color.WHITE);
        mDateSelectedColor = mDateSelectedColor == -1 ? getColor(R.color.colorPrimary) : mDateSelectedColor;
        mDateSelectedTextColor = mDateSelectedTextColor == -1 ? Color.WHITE : mDateSelectedTextColor;
        mTodayDateTextColor = mTodayDateTextColor == -1 ? getColor(R.color.primaryTextColor) : mTodayDateTextColor;
        mDayOfWeekTextColor = mDayOfWeekTextColor == -1 ? getColor(R.color.secundaryTextColor) : mDayOfWeekTextColor;
        mUnselectedDayTextColor = mUnselectedDayTextColor == -1 ? getColor(R.color.primaryTextColor) : mUnselectedDayTextColor;
        rvDays.init(getContext(),
                finalDays,
                finalOffset,
                mBackgroundColor,
                mDateSelectedColor,
                mDateSelectedTextColor,
                mTodayDateTextColor,
                mTodayDateBackgroundColor,
                mDayOfWeekTextColor,
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
        return rvDays.post(action);
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
        tvMonth.setText(item.toString("MMMM YYYY", Locale.getDefault()));
        if (showTodayButton) tvToday.setVisibility(isToday(item) ? INVISIBLE : VISIBLE);
        if (listener != null) {
            listener.onDateSelected(item);
        }
    }

    public int getDays() {
        return days;
    }

    public HorizontalDayPicker setDays(int days) {
        this.days = days;
        return this;
    }

    public int getOffset() {
        return offset;
    }

    public HorizontalDayPicker setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    public HorizontalDayPicker setDateSelectedColor(@ColorInt int color) {
        mDateSelectedColor = color;
        return this;
    }

    public HorizontalDayPicker setDateSelectedTextColor(@ColorInt int color) {
        mDateSelectedTextColor = color;
        return this;
    }

    public HorizontalDayPicker setMonthAndYearTextColor(@ColorInt int color) {
        mMonthAndYearTextColor = color;
        return this;
    }

    public HorizontalDayPicker setTodayButtonTextColor(@ColorInt int color) {
        mTodayButtonTextColor = color;
        return this;
    }

    public HorizontalDayPicker showTodayButton(boolean show) {
        showTodayButton = show;
        return this;
    }

    public HorizontalDayPicker setTodayDateTextColor(int color) {
        mTodayDateTextColor = color;
        return this;
    }

    public HorizontalDayPicker setTodayDateBackgroundColor(@ColorInt int color) {
        mTodayDateBackgroundColor = color;
        return this;
    }

    public HorizontalDayPicker setDayOfWeekTextColor(@ColorInt int color) {
        mDayOfWeekTextColor = color;
        return this;
    }

    public HorizontalDayPicker setUnselectedDayTextColor(@ColorInt int color) {
        mUnselectedDayTextColor = color;
        return this;
    }
}