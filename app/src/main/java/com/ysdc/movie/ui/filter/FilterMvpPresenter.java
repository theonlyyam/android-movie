package com.ysdc.movie.ui.filter;

import com.ysdc.movie.ui.base.MvpPresenter;

import java.util.Date;

/**
 * Action specific to the Filter presenter.
 * Useful for its bottom sheet fragment, to know what are the actions available.
 */

public interface FilterMvpPresenter<V extends FilterMvpView> extends MvpPresenter<V> {

    /**
     * @return a formatted date if we have one, or a '-'
     */
    String getFromDateFormatted();

    /**
     * @return a formatted date if we have one, or a '-'
     */
    String getToDateFormatted();

    /**
     * Store the defined filters
     */
    void saveFilters();

    void setDateFrom(Date date);

    void setDateTo(Date date);

    /**
     * Get the year of the 'from date' attribute, or the current year
     */
    int getfromDateYear();

    /**
     * Get the month of the 'from date' attribute, or the current month
     */
    int getFromDateMonth();

    /**
     * Get the year of the 'to date' attribute, or the current year
     */
    int getToDateYear();

    /**
     * Get the month of the 'to date' attribute, or the current month
     */
    int getToDateMonth();

    /**
     * Remove the filter 'from date'
     */
    void clearDateFrom();

    /**
     * Remove the filter 'to date'
     */
    void clearDateTo();

    /**
     * @return true, if the fields are valid (from date lower than to date)
     */
    boolean fieldsValid();
}