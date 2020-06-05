package com.zero.zero;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zero.zero.data.DonorRegistrationInfo;

public class donor_registration extends AppCompatActivity implements View.OnClickListener {
    private ProgressBar progressBar;
    //FloatingActionButton fab = findViewById(R.id.fab);
    EditText password;
    EditText phone;
    EditText email;
    EditText address;
    EditText city;
    EditText zip;
    EditText contactPhone;
    EditText cuisineType;
    EditText contactPerson;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_registration);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         password = findViewById(R.id.input_password);
         phone = findViewById(R.id.phone);
         email = findViewById(R.id.email);
         address = findViewById(R.id.address);
         city = findViewById(R.id.city);
         zip = findViewById(R.id.zip);
         contactPhone = findViewById(R.id.phone_contact);
         cuisineType = findViewById(R.id.cuisine_type);
         contactPerson = findViewById(R.id.contact_person);
        progressBar = findViewById(R.id.donorRegistrationProgressBar);
        progressBar.setVisibility(View.GONE);

        findViewById(R.id.fabDonationEdit).setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();




/*
fab.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
.setAction("Action", null).show();

}
});
*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null) {
            //handle already logged in user
        }


    }

    private void registerUser() {
        String password_val = password.getText().toString();
        String phone_val = phone.getText().toString();
        String email_val = email.getText().toString();
        String address_val = address.getText().toString();
        String city_val = city.getText().toString();
        String zip_val = zip.getText().toString();
        String contactPhone_val = contactPhone.getText().toString();
        String cuisineType_val = cuisineType.getText().toString();
        String contactPerson_val = contactPerson.getText().toString();

        if(email_val.isEmpty()) {
            email.setError("email required");
            email.requestFocus();
            return;
        }
        if(password_val.isEmpty()) {
            password.setError("password required");
            password.requestFocus();
            return;
        }
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
            cuisineType.setError("cuisine type required");
            cuisineType.requestFocus();
            return;
        }
        if(contactPerson_val.isEmpty()) {
            contactPerson.setError("Contact Person required");
            contactPerson.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email_val,password_val)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()) {

                            DatabaseReference database;

                            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                            //String id = database.push().getKey();
                            String id = currentFirebaseUser.getUid();
                            database = FirebaseDatabase.getInstance().getReference("Donors/");

                            DonorRegistrationInfo donorRegistrationInfo = new DonorRegistrationInfo(id,email.getText().toString()
                                    ,password.getText().toString()
                                    ,phone.getText().toString()
                                    ,address.getText().toString()
                                    ,city.getText().toString()
                                    ,zip.getText().toString()
                                    ,contactPerson.getText().toString()
                                    ,contactPhone.getText().toString()
                                    ,cuisineType.getText().toString());

                            database.child(id).setValue(donorRegistrationInfo)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar.setVisibility(View.GONE);

                                    if(task.isSuccessful()) {
                                        Toast.makeText(donor_registration.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(donor_registration.this,"Unsuccessful",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                        }else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(donor_registration.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.fabDonationEdit:
                registerUser();
                break;

        }
    }
}
