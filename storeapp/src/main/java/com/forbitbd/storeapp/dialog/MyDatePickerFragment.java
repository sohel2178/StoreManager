package com.forbitbd.storeapp.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;


import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.utils.Constant;
import com.google.android.material.button.MaterialButton;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by sohel on 5/20/2018.
 */

public class MyDatePickerFragment extends DialogFragment implements View.OnClickListener {

    private DatePickerListener listener;
    private DatePicker datePicker;
    private TextView tvTitle;
    private MaterialButton btnCancel,btnOk;

    private String titleText;
    private long selectedDate;

    public void setDatePickerListener(DatePickerListener listener){
        this.listener= listener;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleText = getArguments().getString(Constant.TITLE);
        selectedDate = getArguments().getLong(Constant.TIME);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.date_picker, null);
        initView(view);

        AlertDialog alertDialog = new AlertDialog.Builder(getContext(), R.style.MyDialog).create();

        //AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.ThemeOverlay_AppCompat_Dialog);
        alertDialog.setView(view);
        return alertDialog;

    }

    private void initView(View view) {
        datePicker = view.findViewById(R.id.dialog_date_datePicker);
        btnCancel = view.findViewById(R.id.cancel);
        btnOk = view.findViewById(R.id.ok);
        tvTitle = view.findViewById(R.id.title);

        if(selectedDate >0) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(selectedDate));
            datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        }

        if(titleText!=null){
            tvTitle.setText(titleText);
        }

        btnCancel.setOnClickListener(this);
        btnOk.setOnClickListener(this);
    }

    private DatePickerDialog.OnDateSetListener dateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    Toast.makeText(getActivity(), "selected date is " + view.getYear() +
                            " / " + (view.getMonth()+1) +
                            " / " + view.getDayOfMonth(), Toast.LENGTH_SHORT).show();
                }
            };

    @Override
    public void onClick(View view) {

        if(view==btnOk){
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth();
            int year = datePicker.getYear();

            Calendar calendar = Calendar.getInstance();
            calendar.set(year,month,day);

            //calendar.getTimeInMillis()

            if(listener!=null){
                listener.onDatePick(calendar.getTimeInMillis());
            }
            dismiss();
        }else if(view==btnCancel){
            dismiss();
        }
    }
}
