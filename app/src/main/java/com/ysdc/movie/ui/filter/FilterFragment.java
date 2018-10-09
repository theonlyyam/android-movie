package com.ysdc.movie.ui.filter;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.whiteelephant.monthpicker.MonthPickerDialog;
import com.ysdc.movie.R;
import com.ysdc.movie.ui.base.BaseBottomSheetFragment;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Fragment for the search bottom sheet
 */
public class FilterFragment extends BaseBottomSheetFragment implements FilterMvpView {

    @Inject
    FilterMvpPresenter<FilterMvpView> presenter;

    @BindView(R.id.from_date_value)
    protected TextView fromDate;
    @BindView(R.id.to_date_value)
    protected TextView toDate;

    private SearchListener listener;
    private MonthPickerDialog.Builder fromDatePicker;
    private MonthPickerDialog.Builder toDatePicker;

    public interface SearchListener {
        void onSearchPressed();
    }

    public static FilterFragment newInstance(SearchListener listener) {
        FilterFragment filterFragment = new FilterFragment();
        filterFragment.listener = listener;
        return filterFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sheet_filters, container, false);
        getFragmentComponent().inject(this);
        setUnBinder(ButterKnife.bind(this, view));
        presenter.onAttach(FilterFragment.this);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(dialogInterface -> {
            FrameLayout bottomSheet = dialog.findViewById(android.support.design.R.id.design_bottom_sheet);
            BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
            behavior.setSkipCollapsed(true);
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        });
        return dialog;
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void setUp(View view) {
        fromDate.setText(presenter.getFromDateFormatted());
        toDate.setText(presenter.getToDateFormatted());
    }

    @OnClick(R.id.btn_close)
    public void onCloseClicked() {
        dismiss();
    }

    @OnClick(R.id.btn_search)
    public void onSearchClicked() {
        if (presenter.fieldsValid()) {
            presenter.saveFilters();
            listener.onSearchPressed();
            dismiss();
        } else {
            Toast.makeText(getBaseActivity(), R.string.invalid_date, Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.layout_from_date_actions)
    public void changeFromDate() {
        fromDatePicker = new MonthPickerDialog.Builder(getBaseActivity(),
                (selectedMonth, selectedYear) -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(selectedYear, selectedMonth, 1);
                    presenter.setDateFrom(calendar.getTime());
                    fromDate.setText(presenter.getFromDateFormatted());
                }, presenter.getfromDateYear(), presenter.getFromDateMonth());

        fromDatePicker.setTitle(getString(R.string.from_date))
                .build()
                .show();
    }

    @OnClick(R.id.clear_from_date)
    public void clearDateFrom() {
        presenter.clearDateFrom();
        fromDate.setText(presenter.getFromDateFormatted());
    }

    @OnClick(R.id.layout_to_date_action)
    public void changeToDate() {
        toDatePicker = new MonthPickerDialog.Builder(getBaseActivity(),
                (selectedMonth, selectedYear) -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR, selectedYear);
                    calendar.set(Calendar.MONTH, selectedMonth);
                    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                    presenter.setDateTo(calendar.getTime());
                    toDate.setText(presenter.getToDateFormatted());
                }, presenter.getToDateYear(), presenter.getToDateMonth());
        toDatePicker.setTitle(getString(R.string.to_date))
                .build()
                .show();
    }

    @OnClick(R.id.clear_to_date)
    public void clearDateTo() {
        presenter.clearDateTo();
        toDate.setText(presenter.getToDateFormatted());
    }
}
