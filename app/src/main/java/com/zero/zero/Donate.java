package com.zero.zero;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;


import java.util.Calendar;

public class Donate extends Fragment {
    EditText date, fromTime, toTime;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    Toast toast;
    String testGit = "This is a string";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_donor_donate, container, false);
        // initiate the date picker and a button
        date = view.findViewById(R.id.date);
        // perform click event on edit text
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and status from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current status
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(v.getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and status value in the edit text
                                String dateInFormat = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                date.setText(dateInFormat);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        fromTime = view.findViewById(R.id.fromTime);
        toTime = view.findViewById(R.id.toTime);

        fromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        String min=null,hour=null,time;
                        if(minute>=0&&minute<10) {
                            min = "0" + minute;
                        }
                        else
                            min = Integer.toString(minute);
                        if(hourOfDay>=0&&hourOfDay<10) {
                            hour = "0" + hourOfDay;
                        }
                        else
                            hour = Integer.toString(hourOfDay);

                        time = hour + ":" + min;
                        fromTime.setText(time);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        toTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        String min=null,hour=null,time;
                        if(minute>=0&&minute<10) {
                            min = "0" + minute;
                        }
                        else
                            min = Integer.toString(minute);
                        if(hourOfDay>=0&&hourOfDay<10) {
                            hour = "0" + hourOfDay;
                        }
                        else
                            hour = Integer.toString(hourOfDay);

                        time = hour + ":" + min;
                        toTime.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });
        return view;
    }
}