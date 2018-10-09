package com.ysdc.movie.ui.filter;

import com.ysdc.movie.data.prefs.MyPreferences;
import com.ysdc.movie.ui.base.BasePresenter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.ysdc.movie.data.prefs.MyPreferences.FILTER_FROM_DATE;
import static com.ysdc.movie.data.prefs.MyPreferences.FILTER_TO_DATE;
import static com.ysdc.movie.utils.AppConstants.EMPTY_FILTER;
import static com.ysdc.movie.utils.AppConstants.FILTER_DATE_FORMAT;

public class FilterPresenter<V extends FilterMvpView> extends BasePresenter<V> implements FilterMvpPresenter<V> {

    private final MyPreferences preferences;
    private final SimpleDateFormat simpleDateFormat;

    private Calendar fromDate;
    private Calendar toDate;

    public FilterPresenter(MyPreferences preferences) {
        super();
        this.preferences = preferences;
        this.simpleDateFormat = new SimpleDateFormat(FILTER_DATE_FORMAT, Locale.getDefault());
        if (preferences.getAsLong(FILTER_FROM_DATE) > 0) {
            this.fromDate = Calendar.getInstance();
            this.fromDate.setTimeInMillis(preferences.getAsLong(FILTER_FROM_DATE));
        }
        if (preferences.getAsLong(FILTER_TO_DATE) > 0) {
            this.toDate = Calendar.getInstance();
            this.toDate.setTimeInMillis(preferences.getAsLong(FILTER_TO_DATE));
        }
    }

    @Override
    public String getFromDateFormatted() {
        return fromDate != null ? simpleDateFormat.format(fromDate.getTime()) : EMPTY_FILTER;
    }

    @Override
    public String getToDateFormatted() {
        return toDate != null ? simpleDateFormat.format(toDate.getTime()) : EMPTY_FILTER;
    }

    @Override
    public void saveFilters() {
        preferences.set(FILTER_FROM_DATE, fromDate != null ? fromDate.getTimeInMillis() : 0);
        preferences.set(FILTER_TO_DATE, toDate != null ? toDate.getTimeInMillis() : 0);
    }

    @Override
    public void setDateFrom(Date date) {
        fromDate = Calendar.getInstance();
        fromDate.setTime(date);
    }

    @Override
    public void setDateTo(Date date) {
        toDate = Calendar.getInstance();
        toDate.setTime(date);
    }

    @Override
    public int getfromDateYear() {
        if(fromDate == null){
            return Calendar.getInstance().get(Calendar.YEAR);
        }
        return fromDate.get(Calendar.YEAR);
    }

    @Override
    public int getFromDateMonth() {
        if(fromDate == null){
            return Calendar.getInstance().get(Calendar.MONTH);
        }
        return fromDate.get(Calendar.MONTH);
    }

    @Override
    public int getToDateYear() {
        if(toDate == null){
            return Calendar.getInstance().get(Calendar.YEAR);
        }
        return toDate.get(Calendar.YEAR);
    }

    @Override
    public int getToDateMonth() {
        if(toDate == null){
            return Calendar.getInstance().get(Calendar.MONTH);
        }
        return toDate.get(Calendar.MONTH);
    }

    @Override
    public void clearDateFrom() {
        fromDate = null;
    }

    @Override
    public void clearDateTo() {
        toDate = null;
    }

    @Override
    public boolean fieldsValid() {
        return fromDate == null || toDate == null || fromDate.compareTo(toDate) < 0;
    }
}
