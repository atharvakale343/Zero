package com.zero.zero;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.zero.zero.data.DonorRegistrationInfo;

public class MyAccount extends AppCompatActivity {
    private FirebaseDatabase mDatabase;
    private FirebaseUser currentFirebaseUser;
    private String uid;
    private DonorRegistrationInfo donorRegistrationInfo;
    private ProgressBar progressBar;
    Toast toast;
    TextView email;
    EditText phone;
    EditText address;
    EditText city;
    EditText zip;
    EditText contactPerson;
    EditText contactPhone;
    EditText cuisine;
    int clicks;
    int completes = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         email = findViewById(R.id.emailInfo);
         phone = findViewById(R.id.phoneInfo);
         address = findViewById(R.id.addressInfo);
         city = findViewById(R.id.cityInfo);
         zip = findViewById(R.id.zipInfo);
         contactPerson = findViewById(R.id.contact_personInfo);
         contactPhone = findViewById(R.id.phone_contactInfo);
         cuisine = findViewById(R.id.cuisine_typeInfo);
        progressBar = findViewById(R.id.myAccountEditProgressBar);
        disableFields();
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        uid = currentFirebaseUser.getUid();
        clicks = 0;

        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = mDatabase.getReference("Donors/"+uid);
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.VISIBLE);
                donorRegistrationInfo = dataSnapshot.getValue(DonorRegistrationInfo.class);
                //System.out.println(donorRegistrationInfo + "#########");
                //toast.makeText(getApplicationContext(),donorRegistrationInfo.email,Toast.LENGTH_LONG).show();
                setData();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


        final FloatingActionButton fab = findViewById(R.id.fabDonationEdit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicks++;
                if(clicks%2==1) {
                    fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_check_black_24dp));
                    enableFields();
                }
                else if(clicks%2==0) {
                    String phone_val = phone.getText().toString();
                    String address_val = address.getText().toString();
                    String city_val = city.getText().toString();
                    String zip_val = zip.getText().toString();
                    String contactPhone_val = contactPhone.getText().toString();
                    String cuisineType_val = cuisine.getText().toString();
                    String contactPerson_val = contactPerson.getText().toString();

                    if(phone_val.isEmpty()) {
                        phone.setError("phone required");
                        phone.requestFocus();
                        return;
                    }
                    if(address_val.isEmpty()) {
                        address.setError("address required");
                        address.requestFocus();
                        return;
                    }
                    if(city_val.isEmpty()) {
                        city.setError("city required");
                        city.requestFocus();
                        return;
                    }
                    if(zip_val.isEmpty()) {
                        zip.setError("zip required");
                        zip.requestFocus();
                        return;
                    }
                    if(contactPhone_val.isEmpty()) {
                        contactPhone.setError("Contact Phone required");
                        contactPhone.requestFocus();
                        return;
                    }
                    if(cuisineType_val.isEmpty()) {
                        cuisine.setError("cuisine type required");
                        cuisine.requestFocus();
                        return;
                    }
                    if(contactPerson_val.isEmpty()) {
                        contactPerson.setError("Contact Person required");
                        contactPerson.requestFocus();
                        return;
                    }
                    progressBar.setVisibility(View.VISIBLE);
                    DatabaseReference ref = mDatabase.getReference("Donors/"+uid);

                    ref.child("phone").setValue(phone_val).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            completes++;
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    toast.makeText(getApplicationContext(),"Failed to update phone",Toast.LENGTH_SHORT).show();
                                }
                            });
                    ref.child("address").setValue(address_val).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            completes++;
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    toast.makeText(getApplicationContext(),"Failed to update address",Toast.LENGTH_SHORT).show();

                                }
                            });
                    ref.child("city").setValue(city_val).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            completes++;
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    toast.makeText(getApplicationContext(),"Failed to update city",Toast.LENGTH_SHORT).show();
                                    // ...
                                }
                            });
                    ref.child("zip").setValue(zip_val).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            completes++;
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    toast.makeText(getApplicationContext(),"Failed to update ZIP",Toast.LENGTH_SHORT).show();
                                    // ...
                                }
                            });
                    ref.child("phone_contact").setValue(contactPhone_val).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            completes++;
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    toast.makeText(getApplicationContext(),"Failed to update Contact Phone",Toast.LENGTH_SHORT).show();
                                    // ...
                                }
                            });
                    ref.child("contact_person").setValue(contactPerson_val).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            completes++;
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    toast.makeText(getApplicationContext(),"Failed to update Contact Person",Toast.LENGTH_SHORT).show();
                                    // ...
                                }
                            });
                    ref.child("cuisine_type").setValue(cuisineType_val).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            completes++;
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    toast.makeText(getApplicationContext(),"Failed to update Cuisine Type",Toast.LENGTH_SHORT).show();
                                    // ...
                                }
                            });

                        toast.makeText(getApplicationContext(),"Updated Succesfully",Toast.LENGTH_LONG).show();
                        fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_menu_edit));
                        progressBar.setVisibility(View.GONE);
                        disableFields();

                }

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setData() {
        email.setText("Welcome "+ donorRegistrationInfo.email);
        phone.setText(donorRegistrationInfo.phone);
        address.setText(donorRegistrationInfo.address);
        city.setText(donorRegistrationInfo.city);
        zip.setText(donorRegistrationInfo.zip);
        contactPerson.setText(donorRegistrationInfo.contact_person);
        contactPhone.setText(donorRegistrationInfo.phone_contact);
        cuisine.setText(donorRegistrationInfo.cuisine_type);
    }

    private void disableFields() {
        phone.setEnabled(false);
        address.setEnabled(false);
        city.setEnabled(false);
        zip.setEnabled(false);
        contactPerson.setEnabled(false);
        contactPhone.setEnabled(false);
        cuisine.setEnabled(false);
    }
    private void enableFields() {
        phone.setEnabled(true);
        address.setEnabled(true);
        city.setEnabled(true);
        zip.setEnabled(true);
        contactPerson.setEnabled(true);
        contactPhone.setEnabled(true);
        cuisine.setEnabled(true);
    }

}
