package com.zero.zero;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class ViewAndEditDonation extends AppCompatActivity {
    private FirebaseDatabase mDatabase;
    private String uid;
    private Donation donation;
    EditText foodDescription;
    EditText pickupDate;
    EditText fromPickupTime;
    EditText toPickupTime;
    Spinner expiration;
    EditText special;
    private ProgressBar progressBar;
    String[] expirationArray;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    int clicks;
    int completes = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_and_edit_donation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        String key = null;
        if (bundle != null) {
            key = bundle.getString("DonationKey");
        }
        foodDescription = findViewById(R.id.food_descriptionInfo);
        pickupDate = findViewById(R.id.dateInfo);
        fromPickupTime = findViewById(R.id.fromTimeInfo);
        toPickupTime = findViewById(R.id.toTimeInfo);
        expiration = findViewById(R.id.expiration_dateInfo);
        special = findViewById(R.id.special_instructionsInfo);
        progressBar = findViewById(R.id.donor_home_progress_bar);
        disableFields();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentFirebaseUser != null) {
            uid = currentFirebaseUser.getUid();
        }
        clicks = 0;

        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = mDatabase.getReference("Donations/"+uid+"/"+key);
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //progressBar.setVisibility(View.VISIBLE);
                donation = dataSnapshot.getValue(Donation.class);
                setData();
                //progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        final FloatingActionButton fab = findViewById(R.id.fabDonationEdit);
        final String finalKey = key;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicks++;
                if(clicks%2==1) {
                    fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_check_black_24dp));
                    enableFields();
                }
                else if(clicks%2==0) {
                    String foodDescription_val = foodDescription.getText().toString();
                    String pickupDate_val = pickupDate.getText().toString();
                    String fromPickupTime_val = fromPickupTime.getText().toString();
                    String toPickupTime_val = toPickupTime.getText().toString();
                    String expiration_val = expiration.getSelectedItem().toString();
                    String special_val = special.getText().toString();

                    if(foodDescription_val.isEmpty()) {
                        foodDescription.setError("Required");
                        foodDescription.requestFocus();
                        return;
                    }
                    if(pickupDate_val.isEmpty()) {
                        pickupDate.setError("Required");
                        pickupDate.requestFocus();
                        return;
                    }
                    if(fromPickupTime_val.isEmpty()) {
                        fromPickupTime.setError("Required");
                        fromPickupTime.requestFocus();
                        return;
                    }
                    if(toPickupTime_val.isEmpty()) {
                        toPickupTime.setError("Required");
                        toPickupTime.requestFocus();
                        return;
                    }
                    if(expiration_val.isEmpty()) {
                        ((TextView)expiration.getSelectedView()).setError("Required");
                        expiration.requestFocus();
                        return;
                    }
                    if(special_val.isEmpty()) {
                        special.setError("Required");
                        special.requestFocus();
                        return;
                    }
   //                 progressBar.setVisibility(View.VISIBLE);
                    DatabaseReference ref = mDatabase.getReference("Donations/"+uid+"/"+ finalKey);

                    ref.child("foodDescription").setValue(foodDescription_val).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            completes++;
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"Failed to update Food Description", Toast.LENGTH_SHORT).show();
                                }
                            });
                    ref.child("pickupDate").setValue(pickupDate_val).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            completes++;
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"Failed to update Pickup Date",Toast.LENGTH_SHORT).show();

                                }
                            });
                    ref.child("fromPickupTime").setValue(fromPickupTime_val).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            completes++;
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"Failed to update From Pickup Time",Toast.LENGTH_SHORT).show();
                                    // ...
                                }
                            });
                    ref.child("toPickupTime").setValue(toPickupTime_val).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            completes++;
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"Failed to update To Pickup Time",Toast.LENGTH_SHORT).show();
                                    // ...
                                }
                            });
                    ref.child("expiration").setValue(expiration_val).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            completes++;
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"Failed to update Expiration",Toast.LENGTH_SHORT).show();
                                    // ...
                                }
                            });
                    ref.child("special").setValue(special_val).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            completes++;
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"Failed to update Special Instructions",Toast.LENGTH_SHORT).show();
                                    // ...
                                }
                            });

                    Toast.makeText(getApplicationContext(),"Updated Succesfully",Toast.LENGTH_LONG).show();
                    fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_menu_edit));
                    //progressBar.setVisibility(View.GONE);
                    disableFields();

                }
            }
        });

        pickupDate.setOnClickListener(new View.OnClickListener() {
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
                                pickupDate.setText(dateInFormat);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        fromPickupTime.setOnClickListener(new View.OnClickListener() {
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
                        fromPickupTime.setText(time);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        toPickupTime.setOnClickListener(new View.OnClickListener() {
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
                        toPickupTime.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void disableFields() {
        foodDescription.setEnabled(false);
        pickupDate.setEnabled(false);
        fromPickupTime.setEnabled(false);
        toPickupTime.setEnabled(false);
        expiration.setEnabled(false);
        special.setEnabled(false);
    }

    private void enableFields() {
        foodDescription.setEnabled(true);
        pickupDate.setEnabled(true);
        fromPickupTime.setEnabled(true);
        toPickupTime.setEnabled(true);
        expiration.setEnabled(true);
        special.setEnabled(true);
    }

    private void setData() {
        foodDescription.setText(donation.foodDescription);
        pickupDate.setText(donation.pickupDate);
        fromPickupTime.setText(donation.fromPickupTime);
        toPickupTime.setText(donation.toPickupTime);
        expirationArray = getResources().getStringArray(R.array.expiration_dates);
        List<String> list = Arrays.asList(expirationArray);
        expiration.setSelection(list.indexOf(donation.expiration));
        special.setText(donation.special);
    }

}
