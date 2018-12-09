package multibrandinfotech.developer.gmeagro.ViewModel;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import multibrandinfotech.developer.gmeagro.R;

import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.Theme_AppCompat_DayNight_Dialog, (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);

        calendar.add(Calendar.DATE, 0);
        Date currentDate = calendar.getTime();
        datePickerDialog.getDatePicker().setMinDate(currentDate.getTime());

        calendar.add(Calendar.DATE, 30);
        Date maxDate = calendar.getTime();
        datePickerDialog.getDatePicker().setMaxDate(maxDate.getTime());

        return datePickerDialog;
    }
}
